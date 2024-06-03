package com.example.cs360projecttwo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.EditText;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DataDisplayActivity extends AppCompatActivity {

    private GridView gridView;
    private Button allItemsButton; // All items button
    private Button addItemButton; // Add items button

    private DatabaseHelper databaseHelper;
    private ArrayAdapter<String> adapter;
    private List<String> allItemsList;

    // Inside DataDisplayActivity class
    private EditText itemToRemoveEditText;
    private EditText quantityToRemoveEditText;
    private Button removeItemButton; // Remove items button



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        gridView = findViewById(R.id.gridViewData);
        allItemsButton = findViewById(R.id.buttonAllItems); // initialize buttonAllItems
        addItemButton = findViewById(R.id.buttonAddItem); // initialize addItemButton

        databaseHelper = new DatabaseHelper(this);

        allItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllItems();
            }
        });

        // Set onClickListener for addItemButton
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the add_item.xml layout
                View dialogView = getLayoutInflater().inflate(R.layout.add_item, null);

                // Create a dialog to display the layout
                AlertDialog.Builder builder = new AlertDialog.Builder(DataDisplayActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                // Set OnClickListener for the "Add" button inside the add_item.xml layout
                Button addButton = dialogView.findViewById(R.id.buttonAddItem); // Assuming your button ID is addButton
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the EditText fields from the add_item.xml layout
                        EditText itemEditText = dialogView.findViewById(R.id.editTextItem);
                        EditText quantityEditText = dialogView.findViewById(R.id.editTextQuantity);

                        // Get the text entered by the user
                        String newItem = itemEditText.getText().toString().trim();
                        String quantityStr = quantityEditText.getText().toString().trim();

                        // Validate user input
                        if (!newItem.isEmpty() && !quantityStr.isEmpty()) {
                            try {
                                int quantity = Integer.parseInt(quantityStr);
                                addItemToDatabase(newItem, quantity);
                                dialog.dismiss(); // Dismiss the dialog after adding the item
                            } catch (NumberFormatException e) {
                                Toast.makeText(DataDisplayActivity.this, "Invalid quantity. Please enter a valid number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DataDisplayActivity.this, "Please enter both item and quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the add_item.xml layout
                View dialogView = getLayoutInflater().inflate(R.layout.add_item, null);

                // Create a dialog to display the layout
                AlertDialog.Builder builder = new AlertDialog.Builder(DataDisplayActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                // Set OnClickListener for the "Add" button inside the add_item.xml layout
                Button addButton = dialogView.findViewById(R.id.buttonAddItem); // Replace with the actual ID of the button
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the EditText fields from the add_item.xml layout
                        EditText itemEditText = dialogView.findViewById(R.id.editTextItem);
                        EditText quantityEditText = dialogView.findViewById(R.id.editTextQuantity);

                        // Get the text entered by the user
                        String newItem = itemEditText.getText().toString().trim();
                        String quantityStr = quantityEditText.getText().toString().trim();

                        // Validate user input
                        if (!newItem.isEmpty() && !quantityStr.isEmpty()) {
                            try {
                                int quantity = Integer.parseInt(quantityStr);
                                addItemToDatabase(newItem, quantity);
                                dialog.dismiss(); // Dismiss the dialog after adding the item
                            } catch (NumberFormatException e) {
                                Toast.makeText(DataDisplayActivity.this, "Invalid quantity. Please enter a valid number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DataDisplayActivity.this, "Please enter both item and quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        // Inside onCreate method, after setting onClickListener for addItemButton
        removeItemButton = findViewById(R.id.buttonRemoveItem);

        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the add_item.xml layout
                View dialogView = getLayoutInflater().inflate(R.layout.add_item, null);

                // Create a dialog to display the layout
                AlertDialog.Builder builder = new AlertDialog.Builder(DataDisplayActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                // Set OnClickListener for the "Add" button inside the add_item.xml layout
                Button addButton = dialogView.findViewById(R.id.buttonAddItem); // Replace with the actual ID of the button
                addButton.setText("Remove"); // Change the text to "Remove"
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the EditText fields from the add_item.xml layout
                        EditText itemEditText = dialogView.findViewById(R.id.editTextItem);
                        EditText quantityEditText = dialogView.findViewById(R.id.editTextQuantity);

                        // Get the text entered by the user
                        String itemToRemove = itemEditText.getText().toString().trim();
                        String quantityStr = quantityEditText.getText().toString().trim();

                        // Validate user input
                        if (!itemToRemove.isEmpty() && !quantityStr.isEmpty()) {
                            try {
                                int quantityToRemove = Integer.parseInt(quantityStr);
                                removeItemFromDatabase(itemToRemove, quantityToRemove);
                                dialog.dismiss(); // Dismiss the dialog after removing the item
                            } catch (NumberFormatException e) {
                                Toast.makeText(DataDisplayActivity.this, "Invalid quantity. Please enter a valid number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DataDisplayActivity.this, "Please enter both item and quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        // Retrieve reference to the "Increase" button
        Button increaseButton = findViewById(R.id.buttonIncreaseItem);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIncreaseDialog();
            }
        });

        // Retrieve reference to the "Decrease" button
        Button decreaseButton = findViewById(R.id.buttonDecreaseItem);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDecreaseDialog();
            }
        });
    }

    // Method to show the dialog for the "Increase" action
    private void showIncreaseDialog() {
        // Inflate the add_item.xml layout
        View dialogView = getLayoutInflater().inflate(R.layout.add_item, null);

        // Create a dialog to display the layout
        AlertDialog.Builder builder = new AlertDialog.Builder(DataDisplayActivity.this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set OnClickListener for the "Add" button inside the add_item.xml layout
        Button addButton = dialogView.findViewById(R.id.buttonAddItem); // Replace with the actual ID of the button
        addButton.setText("Increase"); // Change the text to "Increase"
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the EditText fields from the add_item.xml layout
                EditText itemEditText = dialogView.findViewById(R.id.editTextItem);
                EditText quantityEditText = dialogView.findViewById(R.id.editTextQuantity);

                // Get the text entered by the user
                String newItem = itemEditText.getText().toString().trim();
                String quantityStr = quantityEditText.getText().toString().trim();

                // Validate user input
                if (!newItem.isEmpty() && !quantityStr.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        // Update the item's quantity in the data source
                        updateItemQuantity(newItem, quantity);
                        // Notify the adapter of the change
                        adapter.notifyDataSetChanged();
                        // Display a Toast message
                        Toast.makeText(DataDisplayActivity.this, "The item has been increased", Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Dismiss the dialog after performing the action
                    } catch (NumberFormatException e) {
                        Toast.makeText(DataDisplayActivity.this, "Invalid quantity. Please enter a valid number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DataDisplayActivity.this, "Please enter both item and quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to update item quantity in the data source
    // Method to update item quantity in the data source
    private void updateItemQuantity(String itemName, int newQuantity) {
        for (int i = 0; i < allItemsList.size(); i++) {
            String item = allItemsList.get(i);
            if (item.startsWith(itemName)) {
                int index = item.lastIndexOf(":");
                int currentQuantity = Integer.parseInt(item.substring(index + 1).trim());
                int updatedQuantity = currentQuantity + newQuantity; // Increase the quantity
                // Update the quantity
                allItemsList.set(i, itemName + " - Quantity: " + updatedQuantity);
                break;
            }
        }
    }


    // Method to show the dialog for the "Decrease" action
    private void showDecreaseDialog() {
        // Inflate the add_item.xml layout
        View dialogView = getLayoutInflater().inflate(R.layout.add_item, null);

        // Create a dialog to display the layout
        AlertDialog.Builder builder = new AlertDialog.Builder(DataDisplayActivity.this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set OnClickListener for the "Add" button inside the add_item.xml layout
        Button decreaseButton = dialogView.findViewById(R.id.buttonAddItem); // Replace with the actual ID of the button
        decreaseButton.setText("Decrease"); // Change the text to "Decrease"
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the EditText fields from the add_item.xml layout
                EditText itemEditText = dialogView.findViewById(R.id.editTextItem);
                EditText quantityEditText = dialogView.findViewById(R.id.editTextQuantity);

                // Get the text entered by the user
                String itemToDecrease = itemEditText.getText().toString().trim();
                String quantityStr = quantityEditText.getText().toString().trim();

                // Validate user input
                if (!itemToDecrease.isEmpty() && !quantityStr.isEmpty()) {
                    try {
                        int quantityToRemove = Integer.parseInt(quantityStr);
                        // Decrease the item's quantity in the data source
                        decreaseItemQuantity(itemToDecrease, quantityToRemove);
                        // Notify the adapter of the change
                        adapter.notifyDataSetChanged();
                        // Display a Toast message
                        Toast.makeText(DataDisplayActivity.this, "The item has been decreased", Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Dismiss the dialog after performing the action
                    } catch (NumberFormatException e) {
                        Toast.makeText(DataDisplayActivity.this, "Invalid quantity. Please enter a valid number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DataDisplayActivity.this, "Please enter both item and quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to decrease item quantity in the data source
    private void decreaseItemQuantity(String itemName, int quantityToRemove) {
        for (int i = 0; i < allItemsList.size(); i++) {
            String item = allItemsList.get(i);
            if (item.startsWith(itemName)) {
                int index = item.lastIndexOf(":");
                int currentQuantity = Integer.parseInt(item.substring(index + 1).trim());
                // Ensure the quantity to remove is not more than the current quantity
                if (currentQuantity >= quantityToRemove) {
                    int newQuantity = currentQuantity - quantityToRemove;
                    // Update the quantity
                    allItemsList.set(i, itemName + " - Quantity: " + newQuantity);
                } else {
                    Toast.makeText(DataDisplayActivity.this, "Not enough quantity to remove", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void displayAllItems() {
        // Create a list to hold all items
        allItemsList = new ArrayList<>();

        // Add items to the list with their quantities
        addItemToList("Tires", 4);
        addItemToList("Soap", 3);
        addItemToList("Shirts", 6);
        addItemToList("Laptops", 10);
        addItemToList("Chairs", 8);
        addItemToList("Canned Goods", 12);
        addItemToList("Plates", 6);
        addItemToList("Pens", 5);
        addItemToList("Hammers", 7);

        // Set up the adapter with all items list
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allItemsList);
        gridView.setAdapter(adapter);
    }

    private void addItemToList(String itemName, int quantity) {
        // Add the item name and its quantity to the list
        allItemsList.add(itemName + " - Quantity: " + quantity);
    }
    private void addItemToDatabase(String newItem, int quantity) {
        if (!newItem.isEmpty()) {
            // Add the new item to the list
            addItemToList(newItem, quantity);

            // Notify the adapter that the dataset has changed
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Item added to list", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to remove item from database
    private void removeItemFromDatabase(String itemToRemove, int quantityToRemove) {
        if (!itemToRemove.isEmpty()) {
            removeItemFromList(itemToRemove, quantityToRemove);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Items have been removed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter an item to remove", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to remove item from list
    private void removeItemFromList(String itemToRemove, int quantityToRemove) {
        for (int i = 0; i < allItemsList.size(); i++) {
            String item = allItemsList.get(i);
            if (item.startsWith(itemToRemove)) {
                int index = item.lastIndexOf(":");
                int quantity = Integer.parseInt(item.substring(index + 1).trim());
                if (quantity >= quantityToRemove) {
                    if (quantity == quantityToRemove) {
                        allItemsList.remove(i);
                    } else {
                        allItemsList.set(i, item.substring(0, index + 1) + " " + (quantity - quantityToRemove));
                    }
                    break;
                } else {
                    Toast.makeText(this, "Not enough quantity to remove", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }




}
