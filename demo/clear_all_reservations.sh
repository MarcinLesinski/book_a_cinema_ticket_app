
echo "You are going to delete all reservations"
echo "Press enter to continue"
echo "or Ctrl + C to cancel"
read
echo
REQ_CLEAR="http://localhost:8080/demo/reservations"
curl -sS -v --request DELETE $REQ_CLEAR

