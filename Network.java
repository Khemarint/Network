import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Network {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
           
                 System.out.println("            ________");
                System.out.println("            | Menu |");
                System.out.println("            --------");
                System.out.println("1. Convert from Decimal to Binary");
                System.out.println("2. Convert from Binary to Decimal");
                System.out.println("3. Find Number of Subnets and Hosts(IP with slash notation)");
                System.out.println("4. Find Number of Subnet bits and Host(IP with Break into network)");
                System.out.println("5. Find Network Address");
                System.out.println("6. Find Broadcast Address");
                System.out.println("7. Credit");
                System.out.println("8. Exit");
                 System.out.print("Please choose an option from the menu Above:");
                
                int choice = scanner.nextInt();
                
                if (choice == 1) {
                    while (true) {
                        try {
                            System.out.println("Enter an IP address (Ex: 200.1.1.10) or -1 to return to the menu:");
                            String ipAddress = scanner.next();
                    
                            // Check if the user wants to go back to the menu
                            if (ipAddress.equals("-1")) {
                                break;
                            }
                    
                            // Split the IP address into octets
                            String[] octets = ipAddress.split("\\.");
                    
                            // Validate the IP address
                            if (octets.length != 4) {
                                throw new IllegalArgumentException("Invalid IP address. Please enter a valid IP address.");
                            }
                    
                            StringBuilder binaryIP = new StringBuilder("The binary representation is: ");
                            for (String octet : octets) {
                                int num = Integer.parseInt(octet);
                    
                                // Validate the octet
                                if (num < 0 || num > 255) {
                                    throw new IllegalArgumentException("Invalid octet. It should be between 0 and 255.");
                                }
                    
                                String binary = Integer.toBinaryString(num);
                    
                                // Add leading zeros to make it an 8-bit number
                                String binaryWithLeadingZeros = String.format("%8s", binary).replace(' ', '0');
                    
                                binaryIP.append(binaryWithLeadingZeros).append(" ");
                            }
                            System.out.println(binaryIP.toString());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next();  // Discard the invalid input
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid input. " + e.getMessage());
                        }
                    }
                    
                } else if (choice == 2) {
                    while (true) {
                        try {
                            System.out.println("Enter a binary IP address (Ex: 11001000.00000001.00000001.00001010) or -1 to return to the menu:");
                            String binaryIP = scanner.next();
                    
                            // Check if the user wants to go back to the menu
                            if (binaryIP.equals("-1")) {
                                break;
                            }
                    
                            // Split the binary IP address into octets
                            String[] octets = binaryIP.split("\\.");
                    
                            // Validate the binary IP address
                            if (octets.length != 4) {
                                throw new IllegalArgumentException("Invalid binary IP address. Please enter a valid binary IP address.");
                            }
                    
                            StringBuilder decimalIP = new StringBuilder();
                            for (String octet : octets) {
                                // Validate the octet
                                if (!octet.matches("[01]{8}")) {
                                    throw new IllegalArgumentException("Invalid octet. It should be an 8-bit binary number.");
                                }
                    
                                // Convert binary to decimal
                                int decimal = Integer.parseInt(octet, 2);
                    
                                decimalIP.append(decimal).append(".");
                            }
                            // Remove the last dot
                            decimalIP.setLength(decimalIP.length() - 1);
                    
                            System.out.println("The decimal representation of the IP address is: " + decimalIP.toString());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid binary number. Binary numbers can only contain 0s and 1s.");
                            scanner.next();  // Discard the invalid input
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid input. " + e.getMessage());
                        }
                    }
                    
                } else if (choice == 3) {
                    while (true) {
                        System.out.println("Enter an IP address in slash notation (Ex: 192.168.10.150/28) or -1 to go back to the menu:");
                        String ipAddress = scanner.next();
                    
                        // Check if the user wants to go back to the menu
                        if (ipAddress.equals("-1")) {
                            break;
                        }
                    
                        try {
                            // Split the IP address and subnet mask
                            String[] parts = ipAddress.split("/");
                            String ip = parts[0];
                            int subnet = Integer.parseInt(parts[1]);
                    
                            // Validate the IP address
                            if (!ip.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
                                throw new IllegalArgumentException("Invalid IP address. Please enter a valid IP address.");
                            }
                    
                            // Validate the subnet mask
                            if (subnet < 1 || subnet > 32) {
                                throw new IllegalArgumentException("Invalid subnet mask. It should be between 1 and 32.");
                            }
                    
                            // Calculate the number of subnets
                            int subnets;
                            if (subnet > 24) {
                                subnets = (int) Math.pow(2, subnet - 24);
                            } else if (subnet > 16) {
                                subnets = (int) Math.pow(2, subnet - 16);
                            } else if (subnet > 8) {
                                subnets = (int) Math.pow(2, subnet - 8);
                            } else {
                                subnets = 1;
                            }
                    
                            // Calculate the number of hosts
                            int hosts = (int) Math.pow(2, 32 - subnet) - 2;
                    
                            // Calculate the number of subnet bits and host bits
                            int subnetBits = (int) Math.ceil(Math.log(subnets) / Math.log(2));
                            int hostBits = (int) Math.ceil(Math.log(hosts + 2) / Math.log(2));  // add 2 to account for network and broadcast addresses
                    
                            // Calculate the subnet mask
                            StringBuilder subnetMask = new StringBuilder();
                            for (int i = 0; i < subnet; i++) {
                                subnetMask.append("1");
                                if ((i + 1) % 8 == 0 && i != 31) {
                                    subnetMask.append(".");
                                }
                            }
                            for (int i = subnet; i < 32; i++) {
                                subnetMask.append("0");
                                if ((i + 1) % 8 == 0 && i != 31) {
                                    subnetMask.append(".");
                                }
                            }
                    
                            System.out.println("The IP address is: " + ip);
                            System.out.println("The subnet mask is: " + subnetMask.toString());
                            System.out.println("The number of subnets is: " + subnets + " (Subnet bits: " + subnetBits + ")");
                            System.out.println("The number of hosts is: " + hosts + " (Host bits: " + hostBits + ")");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next();  // Discard the invalid input
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Invalid input. Please enter a valid IP address in slash notation.");
                            scanner.next();  // Discard the invalid input
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid input. " + e.getMessage());
                        }
                    }
                    
                } else if (choice == 4) {
                    while (true) {
                        System.out.println("Enter the IP address (Ex: 200.1.1.10) or -1 to go back to the menu:");
                        String ip = scanner.next();
                    
                        if (ip.equals("-1")) {
                            break;
                        }
                    
                        // Validate the IP address
                        if (!ip.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
                            System.out.println("Invalid IP address. Please enter a valid IP address.");
                            continue;
                        }
                    
                        System.out.println("Enter the number of networks or -1 to go back to the menu:");
                        int numNetworks = 0;
                        if (scanner.hasNextInt()) {
                            numNetworks = scanner.nextInt();
                            scanner.nextLine();  // Consume newline left-over
                        } else {
                            scanner.nextLine();  // Consume the invalid token
                            continue;  // Skip to the next iteration if the input is invalid
                        }
                    
                        if (numNetworks == -1) {
                            break;
                        }
                    
                        try {
                            if (numNetworks <= 0) {
                                throw new IllegalArgumentException("Number of networks must be positive.");
                            }
                    
                            int subnetBits = (int) Math.ceil(Math.log(numNetworks) / Math.log(2));
                    
                            int classBits;
                            String subnetMask = "";
                            if (ip.startsWith("10.")) {
                                classBits = 8;
                                subnetMask = "11111111.00000000.00000000.00000000";
                            } else if (ip.startsWith("172.") || ip.startsWith("192.")) {
                                classBits = 16;
                                subnetMask = "11111111.11111111.00000000.00000000";
                            } else {
                                classBits = 24;
                                subnetMask = "11111111.11111111.11111111.00000000";
                            }
                    
                            // Calculate subnet mask
                            String[] subnetMaskParts = subnetMask.split("\\.");
                            for (int i = 0; i < subnetBits; i++) {
                                int partIndex = (classBits + i) / 8;
                                if (partIndex >= subnetMaskParts.length) {
                                    break;
                                }
                                int bitIndex = (classBits + i) % 8;
                                char[] partBits = subnetMaskParts[partIndex].toCharArray();
                                if (bitIndex >= partBits.length) {
                                    break;
                                }
                                partBits[bitIndex] = '1';
                                subnetMaskParts[partIndex] = new String(partBits);
                            }
                            
                            
                            subnetMask = String.join(".", subnetMaskParts);
                    
                            int totalBits = classBits + subnetBits;
                            if (totalBits > 32) {
                                throw new IllegalArgumentException("Too many bits for IP address.");
                            }
                    
                            int hostBits = 32 - totalBits;
                    
                            int numHosts = (int) Math.pow(2, hostBits) - 2;
                    
                            System.out.println("Number of subnets: " + numNetworks);
                            System.out.println("Subnet bits: " + subnetBits);
                            System.out.println("Subnet mask: " + subnetMask);
                            System.out.println("Number of hosts: " + numHosts + " (Host bits: " + hostBits + ")");
                        } catch (Exception e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                    }
                    
                } else if (choice == 5) {
                    while (true) {
                       
                            System.out.println("Enter the first binary IP address (Ex: 11000000 10101000 00001010 10010110) or -1 to go back to the menu:");
                            scanner.nextLine();  // Consume newline left-over
                            String ip1 = scanner.nextLine().trim();
                            if (ip1.equals("-1")) {
                                 break;
                            }
                            System.out.println("Enter the second binary IP address (Ex: 11111111 11111111 11111111 11110000) or -1 to go back to the menu:");
                            String ip2 = scanner.nextLine().trim();
                            if (ip2.equals("-1")) {
                                 break;
                            }
                         try {
                            
                            // Validate the binary IP addresses
                            if (!ip1.matches("([01]{8} ){3}[01]{8}") || !ip2.matches("([01]{8} ){3}[01]{8}")) {
                                throw new IllegalArgumentException("Invalid binary IP address. Binary IP addresses must be four octets separated by spaces.");
                            }
                            
                            // Convert the binary IP addresses to integers
                            String[] ip1Parts = ip1.split(" ");
                            String[] ip2Parts = ip2.split(" ");
                            int result = 0;
                            for (int i = 0; i < 4; i++) {
                                int num1 = Integer.parseInt(ip1Parts[i], 2);
                                int num2 = Integer.parseInt(ip2Parts[i], 2);
                                
                                // Perform a bitwise AND operation
                                result = (result << 8) | (num1 & num2);
                            }
                            
                            // Convert the result to binary and add spaces after each 8-bit segment
                            String resultBinary = String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0');
                            resultBinary = resultBinary.replaceAll("(.{8})", "$1 ").trim();
                            
                            // Convert the result to decimal
                            String resultDecimal = String.format("%d.%d.%d.%d", (result >> 24) & 255, (result >> 16) & 255, (result >> 8) & 255, result & 255);
                            
                            System.out.println("The Network address in binary is: " + resultBinary);
                            System.out.println("The Network address in decimal is: " + resultDecimal);
                            
                            // If the input is valid, break out of the loop
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            // Don't call scanner.next() here, so the user can try again
                        }
                    }
                } else if (choice == 6) {
                    while (true) {
                      
                            System.out.println("Enter the binary of Network address (Ex: 11000000 10101000 00001010 10010000) or -1 to go back to the menu:");
                            scanner.nextLine();  // Consume newline left-over
                            String ip = scanner.nextLine().trim();
                            if (ip.equals("-1")) {
                                 break;
                            }
                            System.out.println("Enter the number of Host bits or -1 to go back to the menu:");
                            int hostBits = scanner.nextInt();
                              if (hostBits==-1) {
                                 break;
                            }
                        try {
                            // Validate the binary IP address and number of host bits
                            if (!ip.matches("([01]{8} ){3}[01]{8}") || hostBits < 0 || hostBits > 32) {
                                throw new IllegalArgumentException("Invalid binary IP address or number of host bits. Binary IP addresses must be four octets separated by spaces, and the number of host bits must be between 0 and 32.");
                            }
                            
                            // Convert the binary IP address to an integer
                            String[] ipParts = ip.split(" ");
                            int ipAddress = 0;
                            for (int i = 0; i < 4; i++) {
                                ipAddress = (ipAddress << 8) | Integer.parseInt(ipParts[i], 2);
                            }
                            
                            // Calculate the broadcast address by flipping the last n bits of the IP address
                            int broadcastAddress = ipAddress | ((1 << hostBits) - 1);
                            
                            // Convert the broadcast address to binary and add spaces after each 8-bit segment
                            String broadcastAddressBinary = String.format("%32s", Integer.toBinaryString(broadcastAddress)).replace(' ', '0');
                            broadcastAddressBinary = broadcastAddressBinary.replaceAll("(.{8})", "$1 ").trim();
                            
                            // Convert the broadcast address to decimal
                            String broadcastAddressDecimal = String.format("%d.%d.%d.%d", (broadcastAddress >> 24) & 255, (broadcastAddress >> 16) & 255, (broadcastAddress >> 8) & 255, broadcastAddress & 255);
                            
                            System.out.println("The Broadcast address in binary is: " + broadcastAddressBinary);
                            System.out.println("The Broadcast address in decimal is: " + broadcastAddressDecimal);
                            
                            // If the input is valid, break out of the loop
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            // Don't call scanner.next() here, so the user can try again
                        }
                    } 
                } else if (choice == 7) {
                    Scanner fileScanner = null;
                    try {
                        File file = new File("Credit.txt"); // replace with your file name
                        fileScanner = new Scanner(file);
                
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            System.out.println(line);
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    } finally {
                        if (fileScanner != null) {
                            fileScanner.close();
                        }
                    }
                } else if (choice == 8) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2, 3, 4, 5, 6, 7 or 8.");
                }
        }
        
        System.out.println("Thank you for using this program ^.^");
        scanner.close();
    }
}
