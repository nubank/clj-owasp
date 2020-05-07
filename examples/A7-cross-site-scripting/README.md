# A7 - Cross-Site Scripting
XSS flaws occur whenever an application includes untrusted data in a new web page without proper validation or escaping, or updates an existing web page with user-supplied data using a browser API that can create HTML or JavaScript. XSS allows attackers to execute scripts in the victim’s browser which can hijack user sessions, deface web sites, or redirect the user to malicious sites.

# Example
This dummy example with [reagent](https://reagent-project.github.io/) displays user profile information:
```clojure
(defn show-user-profile [id]
  (fn []
    (let [{:keys [username linkedin]} (get-user! id)]
      [:div
       [:p [:strong "Username: "] username]
       [:p [:strong "LinkedIn: "]
        [:a {:href linkedin} "LinkedIn Profile"]]])))
 ```
Although the HTML tag content is escaped by reagent, the href attribute is not. In this scenario an attacker could provide a javascript payload instead of the LinkedIn profile URL, that will be executed when the link is clicked.

```javascript
javascript: alert(1);window.location.replace(\"http://www.linkedin.com/in/hacker\");
```
This kind of attack occurs only on the victims browser.

# Fix
Always sanitize the user input before sending it back to the browser.
