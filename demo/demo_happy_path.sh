#!/bin/bash

# customize user selections by manipulating these parameters #

readonly I_WANT_SEE_MOVIE_IN_THIS_DAY="2021-09-30"  # demo db contains: 2021-09-30(normal day) and 2021-09-25(weekend day)
readonly I_WANT_SEE_MOVIE_AFTER_THIS_HOUR="9"
readonly I_WANT_SEE_MOVIE_BEFORE_THIS_HOUR="19"
readonly I_CHOOSE_SCREENING="f6801ed7-c68b-4fcc-9e30-450d3cfbcf51"
readonly DEMO_WILL_STOP_BEFORE_EACH_OPERATION=true
# user reservation definition can be found in file: user_reservation_details.json

##############################################################
readonly SERVER_URL="http://localhost:8080"
readonly SEPARATOR="================================================"
userStep(){
  if [ "$DEMO_WILL_STOP_BEFORE_EACH_OPERATION" = "true" ]; then
    echo "Press Enter - to send request"
    read
  fi
}

echo $SEPARATOR
echo
echo "User selected day: ${I_WANT_SEE_MOVIE_IN_THIS_DAY} hours: ${I_WANT_SEE_MOVIE_AFTER_THIS_HOUR} - ${I_WANT_SEE_MOVIE_BEFORE_THIS_HOUR}"
REQ_1="${SERVER_URL}/repertoires?date=${I_WANT_SEE_MOVIE_IN_THIS_DAY}&from-hour=${I_WANT_SEE_MOVIE_AFTER_THIS_HOUR}&to-hour=${I_WANT_SEE_MOVIE_BEFORE_THIS_HOUR}"
echo "request: GET ${REQ_1}"
echo
userStep
curl -sS -v --request GET $REQ_1 | json_pp

echo $SEPARATOR
echo
echo "User choose screening : (id: ${I_CHOOSE_SCREENING} )"
REQ_2="${SERVER_URL}/screenings/${I_CHOOSE_SCREENING}"
echo "request: GET ${REQ_2}"
REQ_3="${SERVER_URL}/tickets"
echo "request: GET $REQ_3"
echo
userStep
curl -sS -v --request GET $REQ_2 | json_pp
curl -sS -v --request GET $REQ_3 | json_pp

echo $SEPARATOR
echo
echo "User sent reservation form"
REQ_4="${SERVER_URL}/reservations"
echo "request: POST ${REQ_4}"
echo
userStep
curl -sS -v --header "Content-Type: application/json" --request POST  --data-binary @user_reservation_details.json $REQ_4 | json_pp

echo
echo $SEPARATOR
echo "have a nice show"
read