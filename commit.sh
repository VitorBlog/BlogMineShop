echo "Qual vai ser o comentario do commit?"
read comment
git add *
git commit -m "comment"
git push origin master
echo "Commit feito!"
