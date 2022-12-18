resource "aws_launch_configuration" "ecore_roles" {
  image_id = data.aws_ami.amazon_linux.id
  instance_type = "t2.micro"
  user_data = file("deploy-script.sh")
}

data "aws_ami" "amazon_linux" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn-ami-hvm-*-x86_64-ebs"]
  }
}

resource "aws_security_group" "ecore_roles_instance" {
  name = "ecore-roles-instance"
  ingress {
    from_port       = 80
    to_port         = 80
    protocol        = "tcp"
    security_groups = [aws_security_group.ecore_roles_lb.id]
  }

  egress {
    from_port       = 0
    to_port         = 0
    protocol        = "-1"
    security_groups = [aws_security_group.ecore_roles_lb.id]
  }

  vpc_id = module.vpc.vpc_id
}
