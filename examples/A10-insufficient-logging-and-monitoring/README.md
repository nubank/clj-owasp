# A10 - Insufficient logging and monitoring 

Insufficient logging and monitoring, coupled with missing or ineffective integration with incident response, allows attackers to further attack systems, maintain persistence, pivot to more systems, and tamper, extract, or destroy data. Most breach studies show time to detect a breach is over 200 days, typically detected by external parties rather than internal processes or monitoring.

# Example

In this dummy example the function login is responsible for authentication.

```clojure
(ns insufficient-logging and monitoring
(def db {:matheus.bernardes "banana"})

(defn login [{:keys [username,password]}]
  (if (= ((key username) db) password)
    true
    false))

(login {:username "matheus.bernardes" :password "banana1" :ipaddress "187.115.25.164"}
```

This code does not **log** how many times an user tried to login. This way an attacker could perform a brute force attack in order to discover an user password.

# Fix

Always log critical action on your application. This way you can create intelligence in the gathered information.
