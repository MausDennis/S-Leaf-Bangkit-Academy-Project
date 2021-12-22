# sleaf
S-Leaf: Leaf recognition for plant information retrieval
is a system in the form of an application that is able to identify different plant species based on the leaf object of the plant. 

<p align="center">
<img src="https://github.com/shrr98/sleaf/blob/main/Logo-leaves.png" width="250" height="250" />
</p>

## Training the Model
To train the model, you can use this [training notebook](ML/training/Training.ipynb).</br>
If you want to use the background images we use on this experiment, you can download it on [this link](https://drive.google.com/file/d/1K8Hvf6wJtrlG9iczYZsow_s7UO5oZ1D4/view?usp=sharing) and upload it to your Google Drive.

## Creating VM instances On GCE
1. From GCP Dashboard click Navigation menu and click compute engine on compute section
2. Make sure you at VM Instance and click create instance
3. Name your instance example instance-2
4. Choose us-central-1 for region and us-central1-a for zone (optional) 
5. On Machine Configuration, on series section choose N1 Series and choose n1-standard-1 on machine type section
6. You can choose any boot disk whatever do you want, for my case i choose ubuntu 18.04
7. Checklist on Allow HTTP traffic and Allow HTTPS traffic on Firewall section (this turn for endpoint case if you want connect it to your android/web application)
8. Click Management, security, disks, networking, sole tenancy
9. Click on Networking
10. Fill flask-server on Network tags
11. At last click create

## Creating VPC network 
The purpose of making VPC network is to connect the network from the VM to the browser
1. Click VPC network on Navigation menu
2. If you already on VPC Network click firewall
3. Click Create Firewall rule
4. On Create Firewall rule name your Firewall rule, on my case i name it allow-flask
5. On Target tags fill with flask-server and On Source IP ranges fill with 0.0.0.0/0
6. Make sure you setting TCP on 5000 (this thing for port)
7. At last click create

## Deploying the Model
[!] After training, make sure that you already save your model in your drive.</br>
If you want to use our pre-trained model, you can download it on our drive:
1. [Model](https://drive.google.com/file/d/1uAap0Hb_ogwAXMGeVJVUjHO-QvQpXs4e/view?usp=sharing).
1. [Label](https://drive.google.com/file/d/1-K-tpanQIdtBaZUbhyAg7V1b3XgFVGnJ/view?usp=sharing)

To deploy the model on Google Compute Engine, make sure you have [docker installed on your machine](https://docs.docker.com/engine/install/ubuntu/), then do following steps:
1. Clone this repository on your VM instance
1. Go to ```server/main_server```.
1. Download your model that's already saved in Google Drive by following [this tutorial](https://medium.com/@acpanjan/download-google-drive-files-using-wget-3c2c025a8b99). </br>
    Save it in ```server/main_server/model```.</br>
    Then, go to the model dir, and unzip the model:
    ```bash
    unzip [YOUR_ZIPPED_MODEL]
    ```
1. Go back to ```server/main_server``` dir. Build your docker image by running this command:
    ```bash
    sudo docker build -t sleaf:latest .
    ```
1. Run your docker image.
    ```bash
    sudo docker run --detach -v "$(pwd)/model/[your model dir]:/app/model/custom_bgmodify" -p 80:8080 sleaf:latest
    ```
1. Test your API. Run this command on your CLI:
    ```bash
    curl http://127.0.0.1/
    ```
    You should get this response:
    ```
    Welcome to S-LEAF!
    ```
    If you have checked **Allow HTTP traffic** on your **Firewall setting**, you can access the API from external machine.
    ```bash
    curl http://[YOUR_VM_EXTERNAL_IP]
    ```
    You can check the detail of the API on [this documentation](server/main_server/README.md). </br>
    To test the model deployment, you need to use the Application.

## Deploy The Endpoints on Compute Engine with ESP
### Deploying the Endpoints Configuration
1. Make sure you are in the ```server/main_server``` directory.
1. Open the [```app.yaml```](server/main_server/app.yaml), and edit these lines based on your project:
    ```yaml
    host: "[ENDPOINT NAME].endpoints.[YOUR PROJECT].cloud.goog"
    x-google-endpoints:
    - name: "[ENDPOINT NAME].endpoints.[YOUR PROJECT].cloud.goog"
    target: "[YOUR VM IP]"
    schemes:
    - "http"
    ```
1. Upload the configuration and create a managed service.
    ```bash
    gcloud endpoints services deploy app.yaml
    ```
1. Use the following command to confirm that the required services are enabled:
    ```bash
    gcloud services list
    ```
    If you do not see the required services listed, enable them:
    ```bash
    gcloud services enable servicemanagement.googleapis.com
    gcloud services enable servicecontrol.googleapis.com
    gcloud services enable endpoints.googleapis.com
    ```
    Also enable your Endpoints service:
    ```bash
    gcloud services enable [ENDPOINTS_SERVICE_NAME]
    ```
    ```ENDPOINTS_SERVICE_NAME``` is the full name you specified on your ```app.yaml``` file as in the ```host``` field.

### Deploying the API Backend
[!] Make sure you have Docker installed in your VM instance.
1. Create your own container network called ```esp_net```:
    ```bash
    sudo docker network create --driver bridge esp_net
    ```
1. Run the S-Leaf server:
    ```bash
    sudo docker run --detach --name sleaf_api -v "$(pwd)/model/[your model dir]:/app/model/custom_bgmodify" sleaf:latest
    ```
1. Run the pre-packaged public ESP Docker container. In the ESP startup options, replace ```SERVICE_NAME``` with the name of your service. This is the same name that you configured in the host field of your OpenAPI document (```app.yaml```).
    ```bash
    sudo docker run \
    --name=esp \
    --detach \
    --publish=80:8080 \
    --net=esp_net \
    gcr.io/endpoints-release/endpoints-runtime:1 \
    --service=SERVICE_NAME \
    --rollout_strategy=managed \
    --backend=sleaf_api:8080
    ```
1. Finally, you can try accessing your API using the service name!
    To confirm, try this command:
    ```bash
    curl http://[SERVICE_NAME]/random
    ```
    and you will get the response as defined [here](server/main_server/README.md)

## Building the Apps
1. Clone the repository to your local device
2. Change BASE_URL variable in Constants class located on utils package to your API URL
3. Run the application on your device 
