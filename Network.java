import java.util.InputMismatchException;
import java.util.Scanner;

public class Network {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.println("Please choose an option from the menu:");
                System.out.println("1. Convert from Decimal to Binary");
                System.out.println("2. Convert from Binary to Decimal");
                System.out.println("3. Find Number of Subnets and Hosts");
                System.out.println("4. Find Network Address");
                System.out.println("5. Find Broadcast Address");
                System.out.println("6. Credit By Khemrent AKA Aegon");
                System.out.println("7. Exit");
                
                int choice = scanner.nextInt();
                
                if (choice == 1) {
                    while (true) {
                        try {
                            System.out.println("Enter a decimal number (or -1 to return to the menu):");
                            int num = scanner.nextInt();  // The decimal number to convert
                            
                            if (num == -1) {
                                break;
                            }
                            
                            String binary = Integer.toBinaryString(num);
                            
                            // Add leading zeros to make it an 8-bit number
                            String binaryWithLeadingZeros = String.format("%8s", binary).replace(' ', '0');
                            
                            System.out.println("The binary representation is: " + binaryWithLeadingZeros);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.next();  // Discard the invalid input
                        }
                    }
                } else if (choice == 2) {
                    while (true) {
                        try {
                            System.out.println("Enter a binary number (or -1 to return to the menu):");
                            String binary = scanner.next();
                            
                            if (binary.equals("-1")) {
                                break;
                            }
                            
                            // Convert binary to decimal
                            int decimal = Integer.parseInt(binary, 2);
                            
                            System.out.println("The decimal representation is: " + decimal);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid binary number. Binary numbers can only contain 0s and 1s.");
                            scanner.next();  // Discard the invalid input
                        }
                    }
                } else if (choice == 3) {
                    try {
                        System.out.println("Enter an IP address in slash notation (e.g., 192.168.1.1/24):");
                        String ipAddress = scanner.next();
                        
                        // Split the IP address and subnet mask
                        String[] parts = ipAddress.split("/");
                        String ip = parts[0];
                        int subnet = Integer.parseInt(parts[1]);
                        
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
                        
                        System.out.println("The IP address is: " + ip);
                        System.out.println("The number of subnets is: " + subnets);
                        System.out.println("The number of hosts is: " + hosts);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input. Please enter a valid IP address in slash notation.");
                        scanner.next();  // Discard the invalid input
                    }
                } else if (choice == 4) {
                    while (true) {
                        try {
                            System.out.println("Enter the first binary IP address (Ex: 11000000 10101000 00001010 10010110):");
                            scanner.nextLine();  // Consume newline left-over
                            String ip1 = scanner.nextLine().trim();
                            System.out.println("Enter the second binary IP address (Ex: 11111111 11111111 11111111 11110000):");
                            String ip2 = scanner.nextLine().trim();
                            
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
                            
                            System.out.println("The result in binary is: " + resultBinary);
                            System.out.println("The result in decimal is: " + resultDecimal);
                            
                            // If the input is valid, break out of the loop
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            // Don't call scanner.next() here, so the user can try again
                        }
                    }
                } else if (choice == 5) {
                    while (true) {
                        try {
                            System.out.println("Enter the binary of Network address (Ex: 11000000 10101000 00001010 10010000):");
                            scanner.nextLine();  // Consume newline left-over
                            String ip = scanner.nextLine().trim();
                            System.out.println("Enter the number of Host bits:");
                            int hostBits = scanner.nextInt();
                            
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
                            
                            System.out.println("The broadcast address in binary is: " + broadcastAddressBinary);
                            System.out.println("The broadcast address in decimal is: " + broadcastAddressDecimal);
                            
                            // If the input is valid, break out of the loop
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            // Don't call scanner.next() here, so the user can try again
                        }
                    } 
                } else if (choice == 6) {
                        System.out.println("This program was created by Khemrent AKA Aegon.");
                } else if (choice == 7) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2, 3, 4, 5, 6 or 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();  // Discard the invalid input
            }
        }
        
        System.out.println("Thank you for using this program ^.^");
        scanner.close();
    }
}
