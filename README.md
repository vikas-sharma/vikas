vikas
=====

right click and select Git bash while on D:/work/vikas folder.

now you are on:
vikas@VIKAS-PC /D/work/myrepo (master) # remember, master is the name of local repository

$ git clone https://github.com/vikas-sharma/vikas.git # equivalent to checkout in svn

$ git init   # You now have a .git directory

$ git config --global user.name "Vikas Sharma" # first time Git setup
$ git config --global user.email vikas.sharma.in@gmail.com # first time Git setup

$ git config core.autocrlf false

$ git remote add vikas https://github.com/vikas-sharma/vikas.git # Push an existing repository.

$ git add .  # You've added the working directory files to the index
$ git commit -m "first commit" # You now have one commit.

$ git status # check status

$ git pull vikas master # first pull in case there are some files to be merged.

$ git push vikas master # now, push to update external repository

$ git log # to check changes

# .gitignore file specifies intentionally untracked files that git should ignore

$ git rm $(git ls-files --deleted) # git remove files which have been deleted