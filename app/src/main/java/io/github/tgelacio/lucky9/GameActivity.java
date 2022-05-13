/*
Lucky 9
PROG3210 Final Project

Activity Name: GameActivity
Purpose:
    Processes the game

Revision History
    Tonnicca Gelacio, 2019-12-01: Created
    Tonnicca Gelacio, 2019-12-01: Code Updated
    Tonnicca Gelacio, 2019-12-04: Code and UI Updated
    Tonnicca Gelacio, 2019-12-06: Code and UI Updated
    Tonnicca Gelacio, 2019-12-07: Code and UI Updated
    Tonnicca Gelacio, 2019-12-08: Code and UI Updated
 */

package io.github.tgelacio.lucky9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // Declarations - Global Variables
    private PlayerDB db;
    List<Card> deck;
    Button btnBet, btnHit, btnStand, btnCashOut;
    ImageView imgDealerCard1, imgDealerCard2, imgDealerCard3,
            imgPlayerCard1, imgPlayerCard2, imgPlayerCard3;
    TextView txtDealerHand, txtPlayerHand, lblResult, lblBetWarning, txtRound, txtPlayerMoney, txtPlayerName;
    Spinner spnBet;
    String playerName, playerBet, playerMoneyDisplay, roundCountDisplay, playerTotalDisplay, dealerTotalDisplay,
            winner, action, resultLabel;
    int playerMoney, playerBetAmount, roundCount, playerTotal, dealerTotal;
    String[] betValues;
    String[] playerCards, dealerCards;
    int[] playerHand, dealerHand;
    Card playerCard1, playerCard2, playerCard3,
            dealerCard1, dealerCard2, dealerCard3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");

        if (playerName == null)
        {
            // Call PlayerNameActivity if a player name has not been entered.
            Intent i = new Intent(getApplicationContext(), PlayerNameActivity.class);

            // Add error message
            i.putExtra("playerNameError", "Name cannot be blank.");

            // Start activity
            startActivity(i);
        }

        else
        {
            // initialize database
            db = new PlayerDB(this);

            // get references to widgets
            imgDealerCard1 = (ImageView) findViewById(R.id.imgDealerCard1);
            imgDealerCard2 = (ImageView) findViewById(R.id.imgDealerCard2);
            imgDealerCard3 = (ImageView) findViewById(R.id.imgDealerCard3);
            imgPlayerCard1 = (ImageView) findViewById(R.id.imgPlayerCard1);
            imgPlayerCard2 = (ImageView) findViewById(R.id.imgPlayerCard2);
            imgPlayerCard3 = (ImageView) findViewById(R.id.imgPlayerCard3);
            txtRound = (TextView) findViewById(R.id.txtRound);
            txtPlayerMoney = (TextView) findViewById(R.id.txtPlayerMoney);
            txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);
            txtDealerHand = (TextView) findViewById(R.id.txtDealerHand);
            txtPlayerHand = (TextView) findViewById(R.id.txtPlayerHand);
            lblResult = (TextView) findViewById(R.id.lblResult);
            lblBetWarning = (TextView) findViewById(R.id.lblBetWarning);
            spnBet = (Spinner) findViewById(R.id.spnBet);
            btnBet = (Button) findViewById(R.id.btnBet);
            btnHit = (Button) findViewById(R.id.btnHit);
            btnStand = (Button) findViewById(R.id.btnStand);
            btnCashOut = (Button) findViewById(R.id.btnCashOut);

            // Set onClickListener
            btnBet.setOnClickListener(this);
            btnHit.setOnClickListener(this);
            btnStand.setOnClickListener(this);
            btnCashOut.setOnClickListener(this);
            spnBet.setOnItemSelectedListener(this);

            // populate spinner
            populateSpinner();

            // start game
            startGame();
        }
    }

    private void populateSpinner() {

        // Spinner Drop down elements
        betValues = new String[]{"5", "10", "25", "50", "100", "All In"};

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, betValues);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnBet.setAdapter(dataAdapter);
    }

    private void startGame() {

        // Initializations
        deck = initializeDeck();
        playerMoney = 150; // set player's money to 150
        roundCount = 0;
        playerCards = new String[3];
        dealerCards = new String[3];
        playerHand = new int[]{0, 0, 0};
        dealerHand = new int[]{0, 0, 0};

        // display player name
        txtPlayerName.setText(playerName);

        // display playerMoney
        playerMoneyDisplay = Integer.toString(playerMoney);
        txtPlayerMoney.setText(playerMoneyDisplay);

        // display roundCount
        roundCountDisplay = Integer.toString(roundCount);
        txtRound.setText(roundCountDisplay);

        // set labels to blank
        lblResult.setText("");
        lblBetWarning.setText("");
        lblBetWarning.setVisibility(View.GONE);
        txtDealerHand.setText("Dealer");
        txtPlayerHand.setText("Player's Hand:");

        imgPlayerCard1.setImageResource(0);
        imgPlayerCard2.setImageResource(0);
        imgDealerCard1.setImageResource(0);
        imgDealerCard2.setImageResource(0);

        imgPlayerCard3.setVisibility(View.GONE);
        imgDealerCard3.setVisibility(View.GONE);

        // enable/disable buttons
        btnBet.setEnabled(true);
        btnHit.setEnabled(false);
        btnStand.setEnabled(false);
        btnCashOut.setEnabled(false);
        spnBet.setEnabled(true);
        
    }

    private List<Card> initializeDeck() {

        List<Card> deck = new ArrayList<Card>();

        deck.add(new Card("s1", 1)); // add spades
        deck.add(new Card("s2", 2));
        deck.add(new Card("s3", 3));
        deck.add(new Card("s4", 4));
        deck.add(new Card("s5", 5));
        deck.add(new Card("s6", 6));
        deck.add(new Card("s7", 7));
        deck.add(new Card("s8", 8));
        deck.add(new Card("s9", 9));
        deck.add(new Card("s10", 10));
        deck.add(new Card("c1", 1)); // add clubs
        deck.add(new Card("c2", 2));
        deck.add(new Card("c3", 3));
        deck.add(new Card("c4", 4));
        deck.add(new Card("c5", 5));
        deck.add(new Card("c6", 6));
        deck.add(new Card("c7", 7));
        deck.add(new Card("c8", 8));
        deck.add(new Card("c9", 9));
        deck.add(new Card("c10", 10));
        deck.add(new Card("h1", 1)); // add hearts
        deck.add(new Card("h2", 2));
        deck.add(new Card("h3", 3));
        deck.add(new Card("h4", 4));
        deck.add(new Card("h5", 5));
        deck.add(new Card("h6", 6));
        deck.add(new Card("h7", 7));
        deck.add(new Card("h8", 8));
        deck.add(new Card("h9", 9));
        deck.add(new Card("h10", 10));
        deck.add(new Card("d1", 1)); // add diamonds
        deck.add(new Card("d2", 2));
        deck.add(new Card("d3", 3));
        deck.add(new Card("d4", 4));
        deck.add(new Card("d5", 5));
        deck.add(new Card("d6", 6));
        deck.add(new Card("d7", 7));
        deck.add(new Card("d8", 8));
        deck.add(new Card("d9", 9));
        deck.add(new Card("d10", 10));

        return deck;
    }



    private void startRound() {

        if (deck.size() < 6)
        {
            deck.clear();
            deck = initializeDeck();
            showShuffleDeckDialog();
        }

        // set playerBet
        playerBet = spnBet.getSelectedItem().toString();

        if (playerBet.equals("All In")) {
            playerBetAmount = playerMoney;
        }
        else {
            playerBetAmount = Integer.parseInt(playerBet);
        }

        // check if player has enough money for bet
        if (playerBetAmount <= playerMoney) {

            // increment roundCount
            roundCount++;

            // display roundCount
            roundCountDisplay = Integer.toString(roundCount);
            txtRound.setText(roundCountDisplay);

            // re-initialize variables
            for (int i = 0; i < playerCards.length; i++) {
                playerCards[i] = null;
                dealerCards[i] = null;
                playerHand[i] = 0;
                dealerHand[i] = 0;
            }
            playerTotal = 0;
            dealerTotal = 0;
            playerCard1 = null;
            playerCard2 = null;
            playerCard3 = null;
            dealerCard1 = null;
            dealerCard2 = null;
            dealerCard3 = null;
            winner = "none";

            imgPlayerCard1.setImageResource(0);
            imgPlayerCard2.setImageResource(0);
            imgDealerCard1.setImageResource(0);
            imgDealerCard2.setImageResource(0);

            imgPlayerCard3.setVisibility(View.GONE);
            imgDealerCard3.setVisibility(View.GONE);

            // clear textviews
            txtPlayerHand.setText("");
            txtDealerHand.setText("");
            lblResult.setText("");
            lblBetWarning.setText("");
            lblBetWarning.setVisibility(View.GONE);

            // deal player cards
            playerCard1 = getCard();
            playerHand[0] = playerCard1.getCardValue();
            playerCards[0] = playerCard1.getCardName();
            playerCard2 = getCard();
            playerHand[1] = playerCard2.getCardValue();
            playerCards[1] = playerCard2.getCardName();

            // display player cards
            int p1ResID = getResID(playerCard1.getCardName());
            imgPlayerCard1.setImageResource(p1ResID);
            int p2ResID = getResID(playerCard2.getCardName());
            imgPlayerCard2.setImageResource(p2ResID);

            // deal dealer cards
            dealerCard1 = getCard();
            dealerHand[0] = dealerCard1.getCardValue();
            dealerCards[0] = dealerCard1.getCardName();
            int d1ResID = getResID(dealerCard1.getCardName());
            imgDealerCard1.setImageResource(d1ResID);

            dealerCard2 = getCard();
            dealerHand[1] = dealerCard2.getCardValue();
            dealerCards[1] = dealerCard2.getCardName();
            imgDealerCard2.setImageResource(R.drawable.back);

            // get hand total
            playerTotal = getHandTotal(playerHand);
            dealerTotal = getHandTotal(dealerHand);

            playerTotalDisplay = Integer.toString(playerTotal);
            dealerTotalDisplay = Integer.toString(dealerTotal);

            txtPlayerHand.setText("Player's Hand: " + playerTotalDisplay);
            txtDealerHand.setText("Dealer");  //hide later

            // check for Lucky9 or Natural9
            winner = checkLucky9(playerTotal, dealerTotal);

            if (winner.equals("none")) {
                // enable/disable buttons
                btnBet.setEnabled(false);
                btnHit.setEnabled(true);
                btnStand.setEnabled(true);
                btnCashOut.setEnabled(false);
                spnBet.setEnabled(false);
            }
            else {
                endRound(winner);
            }
        }
        else {
            lblBetWarning.setVisibility(View.VISIBLE);
            lblBetWarning.setText("You don't have enough money. \n" +
                    "Please choose a different amount.");
        }
    }

    private void continueRound(String action) {

        // deal another card to player if they "hit"
        if (action.equals("hit")) {
            playerCard3 = getCard();
            playerHand[2] = playerCard3.getCardValue();
            playerCards[2] = playerCard3.getCardName();

            int p3ResID = getResID(playerCard3.getCardName());
            imgPlayerCard3.setVisibility(View.VISIBLE);
            imgPlayerCard3.setImageResource(p3ResID);
        }
        // dealer "hit" if hand is <= 5
        if (dealerTotal <= 5) {

            dealerCard3 = getCard();
            dealerHand[2] = dealerCard3.getCardValue();
            dealerCards[2] = dealerCard3.getCardName();
            imgDealerCard3.setVisibility(View.VISIBLE);
            imgDealerCard3.setImageResource(R.drawable.back);
        }

        // get hand total
        playerTotal = getHandTotal(playerHand);
        dealerTotal = getHandTotal(dealerHand);

        // display hand total
        playerTotalDisplay = Integer.toString(playerTotal);
        dealerTotalDisplay = Integer.toString(dealerTotal);

        txtPlayerHand.setText("Player's Hand: " + playerTotalDisplay);
        // txtDealerHand.setText("Dealer's Hand: " + dealerTotalDisplay); // hide later

        // check winner
        winner = checkWinner(playerTotal, dealerTotal);
        endRound(winner);

    }

    private void endRound(String winner) {

        // display dealer cards
        int d2ResID = getResID(dealerCard2.getCardName());
        imgDealerCard2.setImageResource(d2ResID);

        if (dealerCard3 != null)
        {
            int d3ResID = getResID(dealerCard3.getCardName());
            imgDealerCard3.setImageResource(d3ResID);
        }

        // display dealer's hand
        txtDealerHand.setText("Dealer's Hand: " + dealerTotalDisplay);

        // Compute winnings
        if (winner.equals("player")) {
            playerMoney = playerMoney + playerBetAmount;

            if (playerTotal == 9)
            {
                resultLabel = "Lucky 9! You win!";
            }
            else
            {
                resultLabel = "You win!";
            }

        }
        else if (winner.equals("dealer")) {
            playerMoney = playerMoney - playerBetAmount;

            if (dealerTotal == 9)
            {
                resultLabel = "Lucky 9! Dealer wins!";
            }
            else
            {
                resultLabel = "Dealer wins!";
            }

        }
        else if (winner.equals("draw")) {
            resultLabel = "Draw.";
        }

        // Display winner
        lblResult.setText(resultLabel);

        // update player money display
        playerMoneyDisplay = Integer.toString(playerMoney);
        txtPlayerMoney.setText(playerMoneyDisplay);

        if (playerMoney == 0) {
            showBankruptDialog();
            lblResult.setText("Game Over.");

            // enable/disable buttons
            btnBet.setEnabled(false);
            btnHit.setEnabled(false);
            btnStand.setEnabled(false);
            btnCashOut.setEnabled(false);
        }
        else {
            // enable/disable buttons
            btnBet.setEnabled(true);
            btnHit.setEnabled(false);
            btnStand.setEnabled(false);
            btnCashOut.setEnabled(true);
            spnBet.setEnabled(true);
        }
    }

    private void showBankruptDialog() {
        // display dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Game Over")
                .setMessage("You ran out of money! Game over.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showShuffleDeckDialog(){

        // display dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Shuffle Deck")
                .setMessage("End of deck reached. Reshuffling...")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void cashOut() {

        // display dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to cash out?")
                .setPositiveButton("Cash Out", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gameOver();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void gameOver() {

        // add to scoreboard
        try {
            db.insertPlayer(playerName, playerMoney, roundCount);
            Toast.makeText(this, "Added to Leaderboard.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // display text
        lblResult.setText("Game Over.");

        // enable/disable buttons
        btnBet.setEnabled(false);
        btnHit.setEnabled(false);
        btnStand.setEnabled(false);
        btnCashOut.setEnabled(false);
    }

    private String checkLucky9(int playerTotal, int dealerTotal) {

        if (playerTotal == 9 && dealerTotal == 9) {
            winner = "draw";
        }
        else if (playerTotal == 9) {
            winner = "player";
        }
        else if (dealerTotal == 9) {
            winner = "dealer";
        }
        else {
            winner = "none";
        }

        return winner;
    }

    private String checkWinner(int playerTotal, int dealerTotal) {

        if (playerTotal == 9 && dealerTotal == 9) {
            winner = "draw";
        }
        else if (playerTotal == 9) {
            winner = "player";
        }
        else if (dealerTotal == 9) {
            winner = "dealer";
        }
        else {
            if (playerTotal == dealerTotal) {
                winner = "draw";
            }
            else {
                if (playerTotal > dealerTotal) {
                    winner = "player";
                }
                else {
                    winner = "dealer";
                }
            }
        }

        return winner;
    }

    private int getHandTotal(int[] playerHand) {

        int total = 0;

        for (int i = 0; i < playerHand.length; i++) {
            total = total + playerHand[i];
        }

        if (total >= 10) {
            total = total % 10;
        }

        return total;
    }

    private Card getCard() {

        int min = 0;
        int max = deck.size() - 1;

        Card selectedCard;

        // Generate random number
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;

        // Get card from deck
        selectedCard = deck.get(randomNumber);

        // Remove selectedCard from deck
        deck.remove(randomNumber);

        return selectedCard;
    }

    private int getResID(String cardName){
        int resID = getApplicationContext().getResources().getIdentifier(cardName, "drawable",
                getApplicationContext().getPackageName());

        return resID;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBet:
                startRound();
                break;
            case R.id.btnHit:
                action = "hit";
                continueRound(action);
                break;
            case R.id.btnStand:
                action = "stand";
                continueRound(action);
                break;
            case R.id.btnCashOut:
                cashOut();
                break;

            default:
                break;
        }
    }

    ///
    /// Event handler of onCreateOptionsMenu method
    ///
    ///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        return true;
    }

    ///
    /// Event handler of onOptionsItemSelected method
    ///
    ///
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.mnuRestart:
                startGame();
                return true;
            case R.id.mnuPlayerName:
                startActivity(new Intent(getApplicationContext(), PlayerNameActivity.class));
                return true;
            case R.id.mnuHome:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.mnuLeaderboard:
                startActivity(new Intent(getApplicationContext(), LeaderboardActivity.class));
                return true;
            case R.id.mnuInstructions:
                startActivity(new Intent(getApplicationContext(), InstructionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
