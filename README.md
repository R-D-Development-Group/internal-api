# R&D Development Backend

## About

This project provides the core backend infrastructure for the R&D Development project. It handles API requests and interacts with the main database.

## Prerequisites

- **Docker**: Needed to run all the applications.
- **Docker Compose**: Required to orchestrate multi-container applications.

## Build the Project

Run the following command to start all the applications locally:

```bash
docker-compose up -d
```

## Testing the project locally

### Accessing the API

#### Method 1: Using Postman

1. Download the `RD_Development.postman_collection.json` file from the `additional_resources` directory.
2. Import it into [Postman](https://www.postman.com/).
3. Generate the Bearer token
4. Run the **POST** and **GET** requests as examples (include the generated token).
    - Ensure the correct `id` is used for the GET request (e.g., object with `id=1` might not exist).

#### Method 2: Using Swagger (Read-only)

You can view the OpenAPI specification by visiting:

- Swagger UI: `http://localhost:8082/public/swagger-ui/index.html`
- Download OpenAPI Spec (YAML): `http://localhost:8082/public/api-docs.yaml`

### Inspecting logs

To check the logs of the internal application, visit:

`http://localhost:5061` – Kibana will be available for log inspection.

## Keycloak information

For authentication, the project is using Keycloak. Below are some useful links:

- http://localhost:8080
- http://localhost:8080/realms/internal/.well-known/openid-configuration

## Shutdown Instructions

To stop the containerized applications, run:
```bash
docker-compose down
```

## Terraform (For Linux (WSL) / MacOS) to AWS
(Not sure if this works on Windows but you can try)
### Necessary Dependencies
1. Make sure to install Terraform first (https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli)
2. Install the AWS CLI (https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
3. Install Ansible (https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html)

### AWS Lab
1. First run the Sandbox lab all the way at the bottom (https://awsacademy.instructure.com).
2. Then press details > Show next to AWS in the lab Vocareum.
3. In the Credentials pop-up / modal Press **show** next to *AWS CLI*, and copy the content to `~/.aws/credentials` file. If it does not exist create the **credentials** file.
    - Test the connection with the AWS CLI from your local machine with `aws sts get-caller-identity`
4. On the same Credentials pop-up / modal, download the labsuser **PEM** next to *SSH key* and move / copy it to `~/.ssh` 
5. chmod 600 on the ~/.ssh/labsuser.pem 

### Terraform
1. **CD** into the `/terraform` folder in this project
2. Run `terraform init` in the terminal
3. Run `terraform plan` in the terminal, those are all the resources that are going to be created on AWS
4. Run `terraform apply -auto-approve` to provision the resources, you can see the progress in the terminal
5. If you want to destroy the resources, run `terraform destroy -auto-approve `

## Live version
Note add the PORT (i.e. :1010) at the end and use http://
1. Kibana: Go to http://ENTER-AWS-IP-HERE.compute-1.amazonaws.com:5601 to see kibana (Note takes a while to spin up!) (Note 2 HTTP not HTTPS)
2. Keycloak: http://ENTER-AWS-IP-HERE.compute-1.amazonaws.com:8080
3. Swagger UI: http://ENTER-AWS-IP-HERE.compute-1.amazonaws.com:8082/public/swagger-ui/index.html
---

## Copyright

**Authors:** Vakaris Paulavicius, Tabish Nanhekhan
2025 | University of Amsterdam
