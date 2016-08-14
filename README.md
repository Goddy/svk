#SVK

##OPENSHIFT DEPLOY

###Clone the openshift repo, then run the following commands
```
git rm -rf src/ pom.xml
git commit -am "removed default files"
git remote add upstream -m master git://github.com/Goddy/svk.git
git pull -s recursive -X theirs upstream master
```

###Passwords are set by the openshift environment:
```
rhc env-set RECAPTCHA_PUBLIC_KEY=key MAILGUN_API_URL=url MAILGUN_API_KEY=key RECAPTCHA_PRIVATE_KEY=key GMAIL_PASSWORD=password FACEBOOK_SECRET=secret FACEBOOK_ID=id BASE_URL=https://x-svkvoetbal.rhcloud.com CLOUDINARY_API_KEY=key CLOUDINARY_API_SECRET=secret -a APP_NAME
```
