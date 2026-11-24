# Full API test: create -> get -> redirect -> stats -> update -> delete,
# plus validation and 404 checks.
# Requires PowerShell 7+ (uses -SkipHttpErrorCheck).
# Run from the project root with the app running: .\http-tests\api-test.ps1

$base = "http://localhost:8080"
$script:failed = 0
$script:passed = 0

function Send([string]$method, [string]$path, [string]$body, [string]$contentType = "application/json") {
    $params = @{
        Method             = $method
        Uri                = "$base$path"
        SkipHttpErrorCheck = $true
        MaximumRedirection = 0
    }
    if ($null -ne $body -and $body -ne "") {
        $params.Body = $body
        $params.ContentType = $contentType
    }
    Invoke-WebRequest @params
}

function BodyOf([string]$file) {
    Get-Content -Raw "http-tests/$file"
}

function Check([string]$name, [bool]$condition, [string]$detail = "") {
    if ($condition) {
        Write-Host "PASS  $name" -ForegroundColor Green
        $script:passed++
    } else {
        Write-Host "FAIL  $name $detail" -ForegroundColor Red
        $script:failed++
    }
}

function CheckStatus([string]$name, $response, [int]$expected) {
    Check "$name -> $expected" ($response.StatusCode -eq $expected) "(got $($response.StatusCode): $($response.Content))"
}

Write-Host "`n--- Happy path ---"

# POST: create
$r = Send POST "/shorten" (BodyOf "create.json")
CheckStatus "Create short URL" $r 201
$created = $r.Content | ConvertFrom-Json
$code = $created.shortCode
$originalUrl = $created.url
Check "Short code is 7 Base62 characters" ($code -cmatch '^[a-zA-Z0-9]{7}$') "(got '$code')"

# GET: lookup
$r = Send GET "/shorten/$code"
CheckStatus "Get short URL" $r 200
Check "Get returns the original URL" (($r.Content | ConvertFrom-Json).url -eq $originalUrl)

# GET: stats before any redirect
$r = Send GET "/shorten/$code/stats"
CheckStatus "Stats before redirect" $r 200
Check "accessCount starts at 0" (($r.Content | ConvertFrom-Json).accessCount -eq 0)

# GET: redirect (twice)
$r = Send GET "/$code"
CheckStatus "Redirect" $r 302
Check "Location header points to original URL" ("$($r.Headers.Location)" -eq $originalUrl) "(got '$($r.Headers.Location)')"
Send GET "/$code" | Out-Null

# GET: stats after redirects
$r = Send GET "/shorten/$code/stats"
Check "accessCount is 2 after two redirects" (($r.Content | ConvertFrom-Json).accessCount -eq 2) "(got $(($r.Content | ConvertFrom-Json).accessCount))"

# PUT: update
$r = Send PUT "/shorten/$code" (BodyOf "update.json")
CheckStatus "Update short URL" $r 200
$updated = $r.Content | ConvertFrom-Json
$expectedUrl = (BodyOf "update.json" | ConvertFrom-Json).url
Check "Update changed the URL" ($updated.url -eq $expectedUrl)
Check "Update kept the short code" ($updated.shortCode -eq $code)
Check "updatedAt is after createdAt" ([datetime]$updated.updatedAt -gt [datetime]$updated.createdAt)

# Redirect now goes to the new URL
$r = Send GET "/$code"
Check "Redirect uses the updated URL" ("$($r.Headers.Location)" -eq $expectedUrl)

# DELETE
$r = Send DELETE "/shorten/$code"
CheckStatus "Delete short URL" $r 204

Write-Host "`n--- After delete (404) ---"
CheckStatus "Get deleted code"      (Send GET "/shorten/$code") 404
CheckStatus "Stats of deleted code" (Send GET "/shorten/$code/stats") 404
CheckStatus "Redirect deleted code" (Send GET "/$code") 404
CheckStatus "Update deleted code"   (Send PUT "/shorten/$code" (BodyOf "update.json")) 404
CheckStatus "Delete again"          (Send DELETE "/shorten/$code") 404

Write-Host "`n--- Validation (400) ---"
CheckStatus "Empty body {}"           (Send POST "/shorten" (BodyOf "create-empty.json")) 400
CheckStatus "Wrong field name"        (Send POST "/shorten" (BodyOf "create-wrong-field.json")) 400
CheckStatus "Blank url"               (Send POST "/shorten" (BodyOf "create-blank.json")) 400
CheckStatus "ftp:// protocol"         (Send POST "/shorten" (BodyOf "create-invalid-protocol.json")) 400
CheckStatus "javascript: scheme"      (Send POST "/shorten" (BodyOf "create-javascript.json")) 400
CheckStatus "Not a URL"               (Send POST "/shorten" (BodyOf "create-not-a-url.json")) 400
CheckStatus "Malformed JSON"          (Send POST "/shorten" (BodyOf "create-malformed.json")) 400
$tooLong = @{ url = "https://example.com/" + ("a" * 2000) } | ConvertTo-Json
CheckStatus "URL longer than 2000"    (Send POST "/shorten" $tooLong) 400

# Create a valid entry so PUT validation hits a real code, then clean up
$tmp = (Send POST "/shorten" (BodyOf "create.json")).Content | ConvertFrom-Json
CheckStatus "Update with invalid URL" (Send PUT "/shorten/$($tmp.shortCode)" (BodyOf "create-not-a-url.json")) 400
Send DELETE "/shorten/$($tmp.shortCode)" | Out-Null

Write-Host "`n--- Other ---"
CheckStatus "Wrong Content-Type"      (Send POST "/shorten" "url=https://example.com" "text/plain") 415
CheckStatus "Unknown code"            (Send GET "/shorten/doesnotexist") 404

Write-Host "`n$script:passed passed, $script:failed failed"
if ($script:failed -gt 0) { exit 1 }
