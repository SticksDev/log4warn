#!/usr/bin/env python3

import subprocess
import os

print("** Compiling: CHDIR... **")
os.chdir("./payloads/")
print("** Compiling: Invoke javac... **")
subprocess.run(["javac", "Log4Warn.java"])
print("** DONE.. Opening webserver! **")
subprocess.run(["python3", "-m", "http.server", "8888"])

