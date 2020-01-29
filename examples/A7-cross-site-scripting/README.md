# A7 - Cross-Site Scripting
XSS flaws occur whenever an application includes untrusted data in a new web page without proper validation or escaping, or updates an existing web page with user-supplied data using a browser API that can create HTML or JavaScript. XSS allows attackers to execute scripts in the victim’s browser which can hijack user sessions, deface web sites, or redirect the user to malicious sites.

# Example
This dummy example generate a welcome page based on the username.
```clojure
(def logged-user-template "<html>
                          <head> <title>Welcome</title> </head>
                          <body> 
                          <h1>Welcome {{USERNAME}}</h1>
                          </body>
                          </html>")

(defn render-template [username]
  (clojure.string/replace logged-user-template #"\{\{USERNAME\}\}" username))

(defn route-logged-user [username]
  (render-template username))

(route-logged-user "matheus.bernardes")
 ```
In a scenario where this page will be rendered by a browser an attacker could provide a javascript payload which will be evaluated by the browser.
```html
<img src=1 onload=alert() />
<script>alert()</script>
```
This kind of attack occurs only on the victims browser.

# Fix
Always sanitize the user input after sending it back to the browser.
