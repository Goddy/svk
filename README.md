#SVK

##OPENSHIFT DEPLOY

###Clone the openshift repo, then run the following commands
```bash
git rm -rf src/ pom.xml
git commit -am "removed default files"
git remote add upstream -m master git://github.com/Goddy/svk.git
git pull -s recursive -X theirs upstream master
git push
```

###Passwords are set by the openshift environment:
```bash
rhc env-set RECAPTCHA_PUBLIC_KEY=key RECAPTCHA_PRIVATE_KEY=key MAILGUN_API_URL=url MAILGUN_API_KEY=key GMAIL_PASSWORD=password FACEBOOK_SECRET=secret FACEBOOK_ID=id BASE_URL=baseUrl CLOUDINARY_API_KEY=key CLOUDINARY_API_SECRET=secret -a APP_NAME
```

##API's used
* [Mailgun](https://www.mailgun.com/) for emailing, SMTP also possible
* [Cloudinary](http://cloudinary.com/) for images
* [Facebook OAUTH](https://facebook.com/) to login using facebook
* [Google recaptcha](https://www.google.com/recaptcha/intro/index.html)

##CERTS

Certs are generated thanks to [Let's encrypt](https://letsencrypt.org/).

To renew a cert (it lasts for only 90 days), manual steps are needed (thanks to [Velin Georgiev](http://velin-georgiev-blog.appspot.com/blog/how-to-setup-lets-encrypt-ssl-certificate-on-openshift)):

###Install let's encrypt tools
```bash
git clone https://github.com/letsencrypt/letsencrypt
cd letsencrypt
./letsencrypt-auto --help
```

###Generate cert
```bash
sudo ./letsencrypt-auto certonly -a manual -d voetbal.svk-oh.be --server https://acme-v01.api.letsencrypt.org/directory
```
###Renew Cert
```bash
sudo ./letsencrypt-auto renew 
```

###Update certcontroller in code with the hashes supplied by the script, then deploy and continue the script
###Add the certs to openshift
* SSL certificate: fullchain.pem
* Private key: privkey.pem

