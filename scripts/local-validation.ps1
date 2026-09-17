$ErrorActionPreference='Stop'
$root=Split-Path -Parent $PSScriptRoot
$valid=Get-Content "$root/tests/valid-events.json" -Raw | ConvertFrom-Json
$invalid=Get-Content "$root/tests/invalid-events.json" -Raw | ConvertFrom-Json
function IsValid($e) { return $null -ne $e.event_id -and $e.event_id -ne '' -and $null -ne $e.symbol -and $e.symbol -ne '' -and [double]$e.price -gt 0 -and [long]$e.volume -gt 0 -and $null -ne ($e.timestamp -as [datetime]) }
$ok=($valid | Where-Object { IsValid $_ }).Count
$bad=($invalid | Where-Object { -not (IsValid $_) }).Count
if($ok -ne $valid.Count -or $bad -ne $invalid.Count){throw 'fixture validation failed'}
"Fixture validation passed: $ok valid; $bad invalid." | Tee-Object "$root/evidence/local-validation.log"
