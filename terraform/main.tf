provider "aws" {
  region = "us-east-1"
}

# SECURITY GROUPS
resource "aws_security_group" "internal_sg" {
  name        = "internal-security-group"
  description = "Allow necessary ports"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] 
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8082
    to_port     = 8082
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

    egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

    tags = {
        Name = "InternalSG"
    }
}

resource "aws_security_group" "elk_sg" {
  name        = "elk-security-group"
  description = "Allow ELK traffic"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] 
  }

  ingress {
    from_port   = 9200
    to_port     = 9200
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 9300
    to_port     = 9300
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 5044
    to_port     = 5044
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 5601
    to_port     = 5601
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 4560
    to_port     = 4560
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
 }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

    tags = {
        Name = "ElkSG"
    }
}

resource "aws_security_group" "keycloak_sg" {
  name        = "keycloak-security-group"
  description = "Allow keycloak ports"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] 
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

    tags = {
        Name = "KeycloakSG"
    }
}

resource "aws_security_group" "frontend_sg" {
  name        = "frontend-security-group"
  description = "Allow frontend ports"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] 
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 4173
    to_port     = 4173
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

    tags = {
        Name = "FrontendSG"
    }
}

# EC2 INSTANCES
resource "aws_instance" "internal" {
  ami                  = "ami-04b4f1a9cf54c11d0"
  instance_type        = "t3.small"
  iam_instance_profile = "LabInstanceProfile"
  key_name             = "vockey"
  security_groups      = [aws_security_group.internal_sg.name]

  tags = {
    Name = "InternalApp"
  }

    depends_on = [local_file.eks_env, local_file.keycloak_env]

  provisioner "file" {
    source      = "${path.module}/../.eks.env"
    destination = "/home/ubuntu/.eks.env"
  }

  provisioner "file" {
    source      = "${path.module}/../.keycloak.env"
    destination = "/home/ubuntu/.keycloak.env"
  }

  provisioner "file" {
    source      = "${path.module}/../.env"
    destination = "/home/ubuntu/.env"
  }

  provisioner "file" {
    source      = "${path.module}/docker/internal-compose.yml"
    destination = "/home/ubuntu/internal-compose.yml"
  }

    provisioner "local-exec" {
    command = "ANSIBLE_HOST_KEY_CHECKING=False ansible-playbook -i ${self.public_ip}, --private-key ~/.ssh/labsuser.pem -u ubuntu ansible/ansible-internal.yml"
    }

  connection {
    type        = "ssh"
    user        = "ubuntu"
    private_key = file("~/.ssh/labsuser.pem")
    host        = self.public_ip
  }
}

resource "aws_instance" "elk" {
  ami                  = "ami-04b4f1a9cf54c11d0"
  instance_type        = "t3.small"
  iam_instance_profile = "LabInstanceProfile"
  key_name             = "vockey"
  security_groups = [aws_security_group.elk_sg.name]

    provisioner "file" {
        source      = "${path.module}/../.env"
        destination = "/home/ubuntu/.env"
    }

    provisioner "file" {
        source      = "${path.module}/docker/elk-compose.yml"
        destination = "/home/ubuntu/elk-compose.yml"
    }

    provisioner "local-exec" {
    command = "ANSIBLE_HOST_KEY_CHECKING=False ansible-playbook -i ${self.public_ip}, --private-key ~/.ssh/labsuser.pem -u ubuntu ansible/ansible-elk.yml"
    }

  tags = {
    Name = "ELK"
  }

connection {
    type        = "ssh"
    user        = "ubuntu"
    private_key = file("~/.ssh/labsuser.pem")
    host        = self.public_ip
  }
}

resource "aws_instance" "frontend" {
  ami                  = "ami-04b4f1a9cf54c11d0"
  instance_type        = "t3.small"
  iam_instance_profile = "LabInstanceProfile"
  key_name             = "vockey"
  security_groups = [aws_security_group.frontend_sg.name]

  depends_on = [local_file.internal_env]


  provisioner "file" {
      source      = "${path.module}/../.env"
      destination = "/home/ubuntu/.env"
  }

  provisioner "local-exec" {
  command = "ANSIBLE_HOST_KEY_CHECKING=False ansible-playbook -i ${self.public_ip}, --private-key ~/.ssh/labsuser.pem -u ubuntu ansible/ansible-frontend.yml"
  }

  tags = {
    Name = "frontend"
  }

connection {
    type        = "ssh"
    user        = "ubuntu"
    private_key = file("~/.ssh/labsuser.pem")
    host        = self.public_ip
  }
}

resource "aws_instance" "keycloak" {
  ami                  = "ami-04b4f1a9cf54c11d0"
  instance_type        = "t3.small"
  iam_instance_profile = "LabInstanceProfile"
  key_name             = "vockey"
  security_groups = [aws_security_group.keycloak_sg.name]

    provisioner "file" {
        source      = "${path.module}/../.env"
        destination = "/home/ubuntu/.env"
    }

    provisioner "file" {
        source      = "${path.module}/docker/keycloak-compose.yml"
        destination = "/home/ubuntu/keycloak-compose.yml"
    }

    provisioner "local-exec" {
    command = "ANSIBLE_HOST_KEY_CHECKING=False ansible-playbook -i ${self.public_ip}, --private-key ~/.ssh/labsuser.pem -u ubuntu ansible/ansible-keycloak.yml"
    }

  tags = {
    Name = "Keycloak"
  }

connection {
    type        = "ssh"
    user        = "ubuntu"
    private_key = file("~/.ssh/labsuser.pem")
    host        = self.public_ip
  }
}

# ENVIRONMENT VARIABLES
resource "local_file" "eks_env" {
    content  = <<EOT
ELASTICSEARCH_HOST=${aws_instance.elk.public_dns}:9200
LOGSTASH_URL=${aws_instance.elk.public_dns}:4560
    EOT
    filename = "${path.module}/../.eks.env"
}

resource "terraform_data" "append_env" {
  input = {
    elasticsearch_host = "${aws_instance.elk.public_dns}:9200"
    logstash_url       = "${aws_instance.elk.public_dns}:4560"
  }

  provisioner "local-exec" {
    command = <<EOT
    echo 'ELASTICSEARCH_HOST=${self.input.elasticsearch_host}' >> ${path.module}/../.env
    echo 'LOGSTASH_URL=${self.input.logstash_url}' >> ${path.module}/../.env
    EOT
  }
}

resource "local_file" "keycloak_env" {
    content  = <<EOT
KEYCLOAK_URL=${aws_instance.keycloak.public_dns}
KC_HOSTNAME=${aws_instance.keycloak.public_dns}
VITE_LOGIN_URL=http://${aws_instance.keycloak.public_dns}:8080/realms/internal/protocol/openid-connect/token
    EOT
    filename = "${path.module}/../.keycloak.env"
}

resource "terraform_data" "append_keycloak_env" {
  input = {
    keycloak_dns = aws_instance.keycloak.public_dns
  }

  provisioner "local-exec" {
    command = <<EOT
    echo 'KEYCLOAK_URL=${self.input.keycloak_dns}' >> ${path.module}/../.env
    echo 'KC_HOSTNAME=${self.input.keycloak_dns}' >> ${path.module}/../.env
    echo 'VITE_LOGIN_URL=http://${self.input.keycloak_dns}:8080/realms/internal/protocol/openid-connect/token' >> ${path.module}/../.env
    EOT
  }
}

resource "local_file" "internal_env" {
    content  = <<EOT
VITE_API_URL=http://${aws_instance.internal.public_dns}:8082
    EOT
    filename = "${path.module}/../.internal.env"
}

resource "terraform_data" "append_internal_env" {
  input = {
    internal_dns = aws_instance.internal.public_dns
  }

    provisioner "local-exec" {
    command = <<EOT
    echo 'VITE_API_URL=http://${self.input.internal_dns}:8082' >> ${path.module}/../.env
    EOT
  }
}