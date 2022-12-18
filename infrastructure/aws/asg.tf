resource "aws_autoscaling_group" "default" {
  name                 = "ecore_roles"
  min_size             = 1
  max_size             = 3
  desired_capacity     = 1
  launch_configuration = aws_launch_configuration.ecore_roles.name
  vpc_zone_identifier  = module.vpc.public_subnets

  tag {
    key                 = "Name"
    value               = "E-Core Roles"
    propagate_at_launch = true
  }
}

resource "aws_lb" "default" {
  name               = "ecore-roles-lb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.ecore_roles_lb.id]
  subnets            = module.vpc.public_subnets
}

resource "aws_security_group" "ecore_roles_lb" {
  name = "ecore_roles-lb"
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  vpc_id = module.vpc.vpc_id
}

resource "aws_lb_listener" "default" {
  load_balancer_arn = aws_lb.default.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.main.arn
  }
}

resource "aws_lb_target_group" "main" {
  name     = "e-core-roles-tg"
  port     = 80
  protocol = "HTTP"
  vpc_id   = module.vpc.vpc_id
}

resource "aws_autoscaling_attachment" "main" {
  autoscaling_group_name = aws_autoscaling_group.default.id
  alb_target_group_arn   = aws_lb_target_group.main.arn
}