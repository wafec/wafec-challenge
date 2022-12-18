
resource "aws_db_instance" "default" {
  db_name = "ecore_roles"
  engine = "mysql"
  engine_version = "5.7"
  instance_class = "db.t3.micro"
  allocated_storage = 20
  username = var.database_username
  password = var.database_password
}