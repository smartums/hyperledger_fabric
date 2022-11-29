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


