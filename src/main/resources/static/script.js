const API_URL = "/api/contacts";

const contactForm = document.getElementById("contactForm");
const contactTableBody = document.getElementById("contactTableBody");

const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const phoneInput = document.getElementById("phone");

const submitButton = document.getElementById("submitButton");
const cancelButton = document.getElementById("cancelButton");

const searchInput = document.getElementById("searchInput");
const message = document.getElementById("message");

let editingContactId = null;


// Display messages
function showMessage(text) {
    message.textContent = text;
}


// Handle API responses
async function handleResponse(response) {

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || `Request failed: ${response.status}`);
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}


// GET: Display all contacts
async function getAllContacts() {

    try {

        const response = await fetch(API_URL);
        const contacts = await handleResponse(response);

        displayContacts(contacts);

        showMessage(`${contacts.length} contact(s) found.`);

    } catch (error) {

        showMessage("Error: " + error.message);

    }
}


// Display contacts in the HTML table
function displayContacts(contacts) {

    contactTableBody.innerHTML = "";

    if (contacts.length === 0) {

        contactTableBody.innerHTML =
            `<tr><td colspan="5">No contacts found.</td></tr>`;

        return;
    }

    contacts.forEach(contact => {

        const row = document.createElement("tr");

        const values = [
            contact.id,
            contact.name,
            contact.email,
            contact.phone
        ];

        values.forEach(value => {

            const cell = document.createElement("td");

            cell.textContent = value ?? "";

            row.appendChild(cell);

        });

        const actionCell = document.createElement("td");

        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.className = "edit-button";

        editButton.addEventListener("click", () => {
            editContact(contact.id);
        });

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.className = "delete-button";

        deleteButton.addEventListener("click", () => {
            deleteContact(contact.id);
        });

        actionCell.appendChild(editButton);
        actionCell.appendChild(deleteButton);

        row.appendChild(actionCell);

        contactTableBody.appendChild(row);

    });

}


// POST: Add a new contact
contactForm.addEventListener("submit", async function(event) {

    event.preventDefault();

    const contact = {
        name: nameInput.value.trim(),
        email: emailInput.value.trim(),
        phone: phoneInput.value.trim()
    };

    try {

        let response;

        if (editingContactId === null) {

            response = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(contact)
            });

        } else {

            // PUT: Update an existing contact
            response = await fetch(`${API_URL}/${editingContactId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(contact)
            });

        }

        await handleResponse(response);

        if (editingContactId === null) {
            showMessage("Contact added successfully!");
        } else {
            showMessage("Contact updated successfully!");
        }

        resetForm();

        await getAllContacts();

    } catch (error) {

        showMessage("Error: " + error.message);

    }

});


// Load a contact into the form for editing
async function editContact(id) {

    try {

        const response = await fetch(`${API_URL}/${id}`);
        const contact = await handleResponse(response);

        nameInput.value = contact.name;
        emailInput.value = contact.email;
        phoneInput.value = contact.phone;

        editingContactId = id;

        document.getElementById("formTitle").textContent = "Update Contact";

        submitButton.textContent = "Update Contact";

        cancelButton.hidden = false;

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    } catch (error) {

        showMessage("Error: " + error.message);

    }

}


// DELETE: Delete a contact
async function deleteContact(id) {

    const confirmed = confirm("Are you sure you want to delete this contact?");

    if (!confirmed) {
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        await handleResponse(response);

        showMessage("Contact deleted successfully!");

        await getAllContacts();

    } catch (error) {

        showMessage("Error: " + error.message);

    }

}


// GET: Search contacts by name
async function searchContacts() {

    const name = searchInput.value.trim();

    if (!name) {
        getAllContacts();
        return;
    }

    try {

        const response = await fetch(
            `${API_URL}/search?name=${encodeURIComponent(name)}`
        );

        const contacts = await handleResponse(response);

        displayContacts(contacts);

        showMessage(`${contacts.length} matching contact(s) found.`);

    } catch (error) {

        showMessage("Error: " + error.message);

    }

}


// Reset the form
function resetForm() {

    contactForm.reset();

    editingContactId = null;

    document.getElementById("formTitle").textContent = "Add New Contact";

    submitButton.textContent = "Add Contact";

    cancelButton.hidden = true;

}


// Cancel editing
cancelButton.addEventListener("click", resetForm);


// Search button
document.getElementById("searchButton").addEventListener(
    "click",
    searchContacts
);


// Show all contacts button
document.getElementById("showAllButton").addEventListener(
    "click",
    function() {

        searchInput.value = "";

        getAllContacts();

    }
);


// Load contacts when the page opens
getAllContacts();