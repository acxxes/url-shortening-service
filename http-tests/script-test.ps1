$base = "http://localhost:8080"

# POST: create
$created = Invoke-RestMethod -Method Post -Uri "$base/shorten" `
    -ContentType "application/json" -InFile "http-tests/create.json"
Write-Host "Created:" ($created | ConvertTo-Json)

# Store the code in a variable (the chaining step)
$code = $created.shortCode

# GET: using the code from the POST
$fetched = Invoke-RestMethod -Uri "$base/shorten/$code"
Write-Host "Fetched:" ($fetched | ConvertTo-Json)

# run the script: .\http-tests\script-test.ps1