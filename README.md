# hyperledger_fabric
Steps to deploy chaincode in hyperledger-fabric test network

### Download fabric-sample package
```sh
sudo chmod 666 /var/run/docker.sock
```
and then
```sh
sudo curl -sSL https://bit.ly/2ysbOFE | bash -s -- 2.4.2 1.5.2
```

### Compile the project
1. Copy / Paste your `CarShowroom` project folder into below path:
    ```sh
    /<path_to_fabric-sample>/fabric-samples/chaincode
    ```
    ![image](https://user-images.githubusercontent.com/40519952/204625856-1406dd17-433c-47e5-9696-f95b608cab9f.png)
    
2. To complete the project run below commands:
    ```sh
    cd /<path_to_fabric-sample>/fabric-samples/chaincode/Carshowroom
    ```
    and then
    ```sh
    ./gradlew installDist
    ```
    ![image](https://user-images.githubusercontent.com/40519952/204627979-8b434228-2ce7-4464-b39e-fdb68c9a28d2.png)

3. Copy the folders and files from the library `lib`
    
    ![image](https://user-images.githubusercontent.com/40519952/204629971-552dd377-2d27-4e8a-9b3d-082fdddbdd6d.png)

4. Paste it in the main folder `Carshowroom`
    
    ![image](https://user-images.githubusercontent.com/40519952/204630180-3407210a-2153-4c11-9317-eddbecdaff77.png)

5. In the root project folder, navigate to `build >> install` and rename lib folder to `Carshowroom`
   
   ![image](https://user-images.githubusercontent.com/40519952/204630321-ab45eefa-85e7-41ef-adc3-8b47e410427c.png)

6. In the root project folder, go to `build >> install >> Carshowroom` folder and rename `lib.jar` file to `Carshowroom.jar`
    
    ![image](https://user-images.githubusercontent.com/40519952/204630555-5db6df62-51a0-4c2a-aed5-b167130ba7bf.png)

6. In the root project folder, go to `build >> install >> Carshowroom` folder and rename `lib.jar` file to `Carshowroom.jar`



### Package the chaincode
#### Step 1: Setting up the Hyperledger Fabric Test network
1. Navigate to the `test-network` folder by using the following command:
   ```sh
    cd /<path_to_fabric-sample>/fabric-samples/test-network
    ```
 
2. Stop the previously running test network by running the following command:
   ```sh
    sudo ./network.sh down
    ```
    
    ![image](https://user-images.githubusercontent.com/40519952/204633376-37f01c17-22c6-4ee2-89b3-fef0c0364a74.png)

3. Remove the unused docker images by running the following command:
   ```sh
    sudo docker system prune
    ```
    ![image](https://user-images.githubusercontent.com/40519952/204633559-257da5cd-9476-4b3f-bf27-782e43a20b45.png)
    
4. Start the test network by executing the following command:
   ```sh
    sudo ./network.sh up -ca -s couchdb
    ```
    ![image](https://user-images.githubusercontent.com/40519952/204633748-98446087-b063-4a8d-8695-96b1fe47231d.png)
    
5. To create a communication channel for the peers in the test network use the following command:
   ```sh
    sudo ./network.sh createChannel -c samplechannel
    ```
    ![image](https://user-images.githubusercontent.com/40519952/204633973-d79625f3-6275-4a19-9bb5-2e2e8143a7b8.png)
    
6. Check the list of the channels for the test network by executing the following commands:
   ```sh
    sudo chmod 666 /var/run/docker.sock
    ```
    and then
    ```sh
    docker exec peer0.org1.example.com peer channel list
    ```
    ![image](https://user-images.githubusercontent.com/40519952/204634190-300b0b3f-5022-4346-9476-c08c22253c97.png)


#### Step 2: Packaging the carshowroom chaincode
1. Navigate to `fabric-samples/test-network` folder and create `lifecycle_setup.sh` life using the following command:
   ```sh
    nano lifecycle_setup_org1.sh
   ```
   
2. Add the following code in the `lifecycle_setup_org1.sh` file:
   ```sh
    #!/bin/sh
    export PATH=${PWD}/../bin:${PWD}:$PATH
    export FABRIC_CFG_PATH=$PWD/../config/
    export CORE_PEER_TLS_ENABLED=true
    export CORE_PEER_LOCALMSPID="Org1MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
    export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
    export CORE_PEER_ADDRESS=localhost:7051
    export ORDERER_CA=${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204635137-69850a2f-e582-4fdb-b4b5-aae904a09493.png)

    `Note: Press Ctrl + X to save the file.`
    
3. To set up all required environment variables, go to the `fabric-samples/test-network` and run the `lifecycle_setup_org1.sh` file using the command:
   ```sh
    source ./lifecycle_setup_org1.sh
   ```
   
   ![image](https://user-images.githubusercontent.com/40519952/204635560-598bcecb-4191-4e26-aa35-f448418a44d7.png)

4. Run the following command to package the Carshowroom chaincode:
   ```sh
    peer lifecycle chaincode package Carshowroom.tar.gz --path ../chaincode/Carshowroom/build/install/Carshowroom --lang java --label Carshowroom_1
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204635753-23b0f7a1-80e1-46a5-8549-81bff558d504.png)

5. Run the following command to list the newly created Carshowroom.tar.gz package:
   ```sh
    ls
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204635895-d1e1ab50-fde1-430e-ad01-a5a007135d3f.png)


### Install the chaincode
#### Step 1: installing chaincode on Org1
1. Run the following command to install the chaincode on Org1:
   ```sh
    peer lifecycle chaincode install Carshowroom.tar.gz --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE
   ```
   
   ![image](https://user-images.githubusercontent.com/40519952/204637612-5ea04be9-7773-47aa-a37d-fdcd13243743.png)

    `
        "NOTE: In case of Permission denied error, use the following command: sudo chmod -R 777 .
        If the installation is successful, the Chaincode code package identifier is shown in the output."
    `

#### Step 2: Installing chaincode on Org2
1. Navigate to `fabric-samples/test-network` folder and create `lifecycle_setup_org2.sh` life using the following command:
   ```sh
    nano lifecycle_setup_org2.sh
   ```
   `NOTE: Open another Terminal window to install Org2.`
   
2. Write the following code in the `lifecycle_setup.sh` file and save it:
   ```sh
    #!/bin/sh
    export PATH=${PWD}/../bin:${PWD}:$PATH
    export FABRIC_CFG_PATH=$PWD/../config/

    # Environment variables for Org2
    export CORE_PEER_TLS_ENABLED=true
    export CORE_PEER_LOCALMSPID="Org2MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
    export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp
    export CORE_PEER_ADDRESS=localhost:9051
    export ORDERER_CA=${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
   ```   
   ![image](https://user-images.githubusercontent.com/40519952/204638397-5824e7f8-f8a3-4ca3-8c18-f38d99258428.png)

3. To set up all required environment variables, go to the `fabric-samples/test-network` and run the `lifecycle_setup.sh` file using the command:
   ```sh
    source ./lifecycle_setup_org2.sh
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204638624-7f1a46e1-098a-4b21-b999-1851973fa1c6.png)

4. The command to install your chaincode:
    ```sh
    peer lifecycle chaincode install Carshowroom.tar.gz --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE
   ```   
   ![image](https://user-images.githubusercontent.com/40519952/204638802-e2e4d4d5-0be9-4ad7-a56d-8890befb9dbd.png)

   `
    "NOTE: In case of Permission denied error, use the following command: sudo chmod -R 777 .
    If the installation is successful, the Chaincode code package identifier is shown in the output."
   `

#### Step 3: Querying the installed chaincode on Org1 and Org2
1. Run the following command to query the installed chaincode on `Org1`:
   ```sh
    peer lifecycle chaincode queryinstalled --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE
   ```
  ![image](https://user-images.githubusercontent.com/40519952/204639125-79e76624-0f28-4aac-a17a-7a9190f89bce.png)

2. Run the following command to query the installed chaincode on `Org2`:
  ```sh
    peer lifecycle chaincode queryinstalled  --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE
   ```
  ![image](https://user-images.githubusercontent.com/40519952/204639250-e2f90a5e-0e89-45aa-9adf-4e7f43ede247.png)

   `
    Note: The chaincode Package ID will be the same for both Org1 and Org2 because the same package is installed. Copy the Package ID for the next step
   `
 
#### Step 4: Downloading the installed chaincode from Org1
1. Replace the Package ID copied from Step 3.1 in the following command and run it:
   ```sh
    peer lifecycle chaincode getinstalledpackage --package-id Carshowroom_1:7c4dcef914f1521e1ec9ac931c887eab045ed64f8d688d1ebe91ad87eef64005 --output-directory . --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204639528-2efead75-3229-45bc-9260-d5a0bced1733.png)


### Approve the chaincode
#### Step 1: Approving chaincode for `Org1`
1. Run the following command to get the Package ID for the chaincode and copy it:
   ```sh
    peer lifecycle chaincode queryinstalled --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE
   ```

2. Replace the Package ID copied from Step 1.1 in the following command and run it to approve the chaincode definition for the `Org1`:
   ```sh
    peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile $ORDERER_CA -C samplechannel --name Carshowroom --version 1.0 --init-required --package-id Carshowroom_1:7c4dcef914f1521e1ec9ac931c887eab045ed64f8d688d1ebe91ad87eef64005 --sequence 1
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204875456-2f970b6f-2899-4b62-ac1a-eadea6055acc.png)

#### Step 2: Approving chaincode for Org2
1. Run the following command to get the Package ID for the chaincode and copy it:
   ```sh
    peer lifecycle chaincode queryinstalled --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE
   ```
2. Replace the Package ID copied from Step 1.1 in the following command and run it to approve the chaincode definition for the `Org2`:
   ```sh
    peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile $ORDERER_CA -C samplechannel --name Carshowroom --version 1.0 --init-required --package-id Carshowroom_1:7c4dcef914f1521e1ec9ac931c887eab045ed64f8d688d1ebe91ad87eef64005 --sequence 1
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204875909-d50f8fb0-3fee-4028-a4a4-06204a854ab6.png)

### Commit the Chaincode
#### Step 1: Checking commit readiness for Org1 and Org2
1. Run the following command to check commit readiness for `Org1`:
   ```sh
    peer lifecycle chaincode checkcommitreadiness -C samplechannel --name Carshowroom --version 1.0 --sequence 1 --output json --init-required
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204876320-4a54a579-cc7f-4158-bc7f-e2090664ae19.png)

2. Run the following command to check commit readiness for `Org2`:
   ```sh
    peer lifecycle chaincode checkcommitreadiness -C samplechannel --name Carshowroom --version 1.0 --sequence 1 --output json --init-required
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204876493-433484cf-3f66-4f32-a1d5-e8afd3438e5f.png)

#### Step 2: Committing the chaincode definition to samplechannel
1. Run the following command to create a file for channel commit:
   ```sh
    nano lifecycle_setup_Channel_commit.sh
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204876711-1bebf9ab-33a7-4a9e-891b-a564f3b25e65.png)
    
2. Add the following code in the `lifecycle_setup_Channel_commit.sh` file:
   ```sh
    #!/bin/sh
    export PATH=${PWD}/../bin:${PWD}:$PATH
    export FABRIC_CFG_PATH=$PWD/../config/
    export CORE_PEER_TLS_ENABLED=true
    export CORE_PEER_LOCALMSPID="Org1MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE_ORG1=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
    export CORE_PEER_TLS_ROOTCERT_FILE_ORG2=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
    export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
    export CORE_PEER_ADDRESS=localhost:7051
    export ORDERER_CA=${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204876976-3b8f24fa-4220-4a23-b398-f96448d46a92.png)

3. Run the lifecycle_setup_Channel_commit.sh file using the following command:
   ```sh
    source ./lifecycle_setup_Channel_commit.sh
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204877204-017f10ad-d0da-40dc-8337-424f15bc1053.png)

4. Run the following command to commit the chaincode definition to `samplechannel`:
   ```sh
    peer lifecycle chaincode commit -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA -C samplechannel --name Carshowroom --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1 --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2 --version 1.0 --sequence 1 --init-required
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204877396-32f3b8ae-b097-444f-85f2-53119ccfc728.png)

#### Step 3: Querying the committed chaincode from samplechannel
1. Run the following command to query the committed chaincode definition from the `samplechannel`:
   ```sh
    peer lifecycle chaincode querycommitted -C samplechannel --name Carshowroom
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204877623-34449b43-005b-453c-9e03-789b5c4bff15.png)

### Access the Chaincode Functions from `Org1`
1. Invoke `Init` function using the following command:
   ```sh
    peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA -C samplechannel -n Carshowroom --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1 --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2 --isInit -c '{"Args":[]}'
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204879105-367c27eb-6e3b-4b89-91cd-1ca77cf47da8.png)   

2. Invoke `addNewCar` function using the following command:
   ```sh
    peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA -C samplechannel -n Carshowroom --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1 --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2 -c '{"Args":["addNewCar", "2", "Ford","John","1000"]}'
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204879162-e5d62c5a-19b2-4857-874a-bcb986fa7c30.png)

3. Invoke `queryCarById` function using the following command:
   ```sh
    peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA -C samplechannel -n Carshowroom --peerAddresses localhost:7051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG1 --peerAddresses localhost:9051 --tlsRootCertFiles $CORE_PEER_TLS_ROOTCERT_FILE_ORG2 -c '{"Args":["queryCarById", "2"]}'
   ```
   ![image](https://user-images.githubusercontent.com/40519952/204879308-1c824a9b-2577-455f-8b27-8d71b935ea7e.png)
   
