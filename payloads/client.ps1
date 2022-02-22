# Load windows forms lib to use later
Add-Type -AssemblyName System.Windows.Forms

# Load wshell (for the popup) and System.Windows.Forms's lib for the tray notifaction.
$wshell = New-Object -ComObject Wscript.Shell
$global:balmsg = New-Object System.Windows.Forms.NotifyIcon
$path = (Get-Process -id $pid).Path

# Create the tray message, and show it.
$balmsg.Icon = [System.Drawing.Icon]::ExtractAssociatedIcon($path)
$balmsg.BalloonTipIcon = [System.Windows.Forms.ToolTipIcon]::Warning
$balmsg.BalloonTipText = 'Read the popup for more information!'
$balmsg.BalloonTipTitle = "Minecraft will self close in 10 seconds!"
$balmsg.Visible = $true
$balmsg.ShowBalloonTip(60000)


#Show the popup
$popup = $wshell.Popup("The server you are playing on is vulnerable to a remote attack known as log4shell. Report this to the server owner!", 0,"CLOSE MINECRAFT NOW!",0+48)

# Sleep for 10 Seconds
Start-Sleep -Seconds 10 -Verbose

# Kill all the processes with the name "Minecraft" in them.
get-process minecraft -ea 0 | Stop-Process

# Kill all open games (Java) 

Get-Process java -ea 0 | stop-process
Get-Process javaw -ea 0 | stop-process
Get-Process javac -ea 0 | stop-process
