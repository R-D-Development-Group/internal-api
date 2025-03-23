# resource "aws_s3_bucket" "vite_bucket" {
#    bucket_prefix = "rd-frontend-bucket-"

#   lifecycle {
#     ignore_changes = [object_lock_configuration]
#   }
# }

# resource "aws_s3_bucket_website_configuration" "vite_website" {
#   bucket = aws_s3_bucket.vite_bucket.id

#   index_document {
#     suffix = "index.html"
#   }

#   error_document {
#     key = "index.html"
#   }
# }

# resource "aws_cloudfront_origin_access_control" "vite_oac" {
#   name                              = "vite-oac"
#   origin_access_control_origin_type = "s3"
#   signing_behavior                  = "always"
#   signing_protocol                  = "sigv4"
# }

# resource "aws_s3_bucket_policy" "vite_bucket_policy" {
#   bucket = aws_s3_bucket.vite_bucket.id

#   policy = jsonencode({
#     Version = "2012-10-17"
#     Statement = [
#       {
#         Effect = "Allow"
#         Principal = {
#           Service = "cloudfront.amazonaws.com"
#         }
#         Action = "s3:GetObject"
#         Resource = "${aws_s3_bucket.vite_bucket.arn}/*"
#         Condition = {
#           StringEquals = {
#             "AWS:SourceArn" = aws_cloudfront_distribution.vite_distribution.arn
#           }
#         }
#       }
#     ]
#   })
# }

# resource "aws_cloudfront_distribution" "vite_distribution" {
#   origin {
#     domain_name              = aws_s3_bucket.vite_bucket.bucket_regional_domain_name
#     origin_id                = "vite-s3-origin"
#     origin_access_control_id = aws_cloudfront_origin_access_control.vite_oac.id
#   }

#   enabled             = true
#   default_root_object = "index.html"

#   default_cache_behavior {
#     target_origin_id       = "vite-s3-origin"
#     viewer_protocol_policy = "redirect-to-https"
#     allowed_methods        = ["GET", "HEAD", "OPTIONS"]
#     cached_methods         = ["GET", "HEAD"]
#     compress               = true

#     forwarded_values {
#       query_string = false
#       cookies {
#         forward = "none"
#       }
#     }
#   }

#   custom_error_response {
#     error_code         = 404
#     response_code      = 200
#     response_page_path = "/index.html"
#   }

#   restrictions {
#     geo_restriction {
#       restriction_type = "none"
#     }
#   }

#   viewer_certificate {
#     cloudfront_default_certificate = true
#   }
# }

# output "cloudfront_url" {
#   value = aws_cloudfront_distribution.vite_distribution.domain_name
# }

# resource "local_file" "s3_bucket_url_file" {
#   content  = aws_s3_bucket.vite_bucket.bucket
#   filename = "${path.module}/ansible/s3_bucket_url.txt"
# }

# resource "null_resource" "deploy_vite" {
#   provisioner "local-exec" {
#     command = "ANSIBLE_HOST_KEY_CHECKING=False ansible-playbook -i localhost, --private-key ~/.ssh/labsuser.pem -u ubuntu ansible/ansible-internal.yml"
#   }

#   depends_on = [aws_s3_bucket.vite_bucket, local_file.s3_bucket_url_file]
# }
