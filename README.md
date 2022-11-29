# hyperledger_fabric
Steps to deploy chaincode in hyperledger-fabric test network

### Download fabric-sample package
```sh
sudo chmod 666 /var/run/docker.sock
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



 
