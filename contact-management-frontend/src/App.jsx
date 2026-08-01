import { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  // =========================
  // STATE VARIABLES
  // =========================

  const [showForm, setShowForm] = useState(false);

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [errors, setErrors] = useState({});

  const [contacts, setContacts] = useState([]);

  const [editingId, setEditingId] = useState(null);

  const [searchTerm, setSearchTerm] = useState("");

  const [notification, setNotification] = useState({
  message: "",
  type: "",
  });

  const showNotification = (message, type) => {
  setNotification({
    message: message,
    type: type,
  });

  setTimeout(() => {
    setNotification({
      message: "",
      type: "",
    });
  }, 3000);
};
  // =========================
  // FETCH CONTACTS
  // =========================

  const fetchContacts = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8081/api/contacts"
      );

      setContacts(response.data);
    } catch (error) {
      console.error("Error fetching contacts:", error);
    }
  };

  // =========================
  // LOAD CONTACTS ON PAGE LOAD
  // =========================

  useEffect(() => {
    fetchContacts();
  }, []);

  // =========================
  // OPEN ADD CONTACT FORM
  // =========================

  const openAddForm = () => {
    setName("");
    setEmail("");
    setPhone("");

    setEditingId(null);

    setShowForm(true);
  };

  // =========================
  // SAVE NEW CONTACT
  // =========================

  const validateForm = () => {
  const newErrors = {};

  // Name validation
  if (!name.trim()) {
    newErrors.name = "Name is required";
  } else if (name.trim().length < 2) {
    newErrors.name = "Name must be at least 2 characters";
  }

  // Email validation
  if (!email.trim()) {
    newErrors.email = "Email is required";
  } else if (
    !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  ) {
    newErrors.email = "Please enter a valid email address";
  }

  // Phone validation
  if (!phone.trim()) {
    newErrors.phone = "Phone number is required";
  } else if (!/^\d{10}$/.test(phone)) {
    newErrors.phone =
      "Phone number must contain exactly 10 digits";
  }

  setErrors(newErrors);

  // Returns true if there are no errors
  return Object.keys(newErrors).length === 0;
};

  const saveContact = async (e) => {
  e.preventDefault();

  if (!validateForm()) {
    return;
  }

  

    try {
      await axios.post(
        "http://localhost:8081/api/contacts",
        {
          name: name,
          email: email,
          phone: phone,
        }
      );

      showNotification(
  "Contact added successfully!",
  "success"
);

      closeForm();

      fetchContacts();

    } catch (error) {
      console.error("Error saving contact:", error);

      alert("Failed to save contact");
    }
  };

  // =========================
  // OPEN EDIT FORM
  // =========================

  const startEdit = (contact) => {
    setName(contact.name);
    setEmail(contact.email);
    setPhone(contact.phone);

    setEditingId(contact.id);

    setShowForm(true);
  };

  // =========================
  // UPDATE CONTACT
  // =========================

  const updateContact = async (e) => {
  e.preventDefault();

  if (!validateForm()) {
    return;
  }

  try {
      await axios.put(
        `http://localhost:8081/api/contacts/${editingId}`,
        {
          name: name,
          email: email,
          phone: phone,
        }
      );

      showNotification(
  "Contact updated successfully!",
  "success"
);

      closeForm();

      fetchContacts();

    } catch (error) {
      console.error("Error updating contact:", error);

      alert("Failed to update contact");
    }
  };

  // =========================
  // DELETE CONTACT
  // =========================

  const deleteContact = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this contact?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await axios.delete(
        `http://localhost:8081/api/contacts/${id}`
      );

      showNotification(
  "Contact deleted successfully!",
  "success"
);

      fetchContacts();

    } catch (error) {
      console.error("Error deleting contact:", error);

      alert("Failed to delete contact");
    }
  };

  // =========================
  // CLOSE FORM
  // =========================

 const closeForm = () => {
  setName("");
  setEmail("");
  setPhone("");

  setErrors({});

  setEditingId(null);

  setShowForm(false);
};

  // =========================
  // SEARCH CONTACTS
  // =========================

  const filteredContacts = contacts.filter((contact) => {
    return (
      contact.name
        .toLowerCase()
        .includes(searchTerm.toLowerCase()) ||
      contact.email
        .toLowerCase()
        .includes(searchTerm.toLowerCase()) ||
      contact.phone
        .toLowerCase()
        .includes(searchTerm.toLowerCase())
    );
  });

  // =========================
  // UI
  // =========================

  return (
    <div className="app">

    {notification.message && (
  <div className={`notification ${notification.type}`}>
    <span>
      {notification.type === "success" ? "✓" : "⚠"}
    </span>

    {notification.message}
  </div>
)}

      {/* =========================
          HEADER
      ========================= */}

      <header className="header">

        <div className="logo">
          📇 ContactHub
        </div>

        <button
          className="add-button"
          onClick={openAddForm}
        >
          + Add Contact
        </button>

      </header>


      {/* =========================
          HERO SECTION
      ========================= */}

      <section className="hero">

        <h1>
          Manage Your Contacts
        </h1>

        <p>
          Keep all your contacts organized in one place.
        </p>

      </section>


      {/* =========================
          SEARCH
      ========================= */}

      <div className="search-container">

        <input
          type="text"
          placeholder="🔍 Search contacts by name, email or phone..."
          value={searchTerm}
          onChange={(e) =>
            setSearchTerm(e.target.value)
          }
        />

      </div>


      {/* =========================
          CONTACT SECTION HEADER
      ========================= */}

      <div className="section-header">

        <h2>
          My Contacts
        </h2>

        <span>
          {filteredContacts.length} Contact
          {filteredContacts.length !== 1
            ? "s"
            : ""}
        </span>

      </div>


      {/* =========================
          CONTACT CARDS
      ========================= */}

      <div className="contact-grid">

        {filteredContacts.length === 0 ? (

          <div className="empty-state">

            <div className="empty-icon">
              📭
            </div>

            <h3>
              No Contacts Found
            </h3>

            <p>
              Add your first contact to get started.
            </p>

            <button
              className="add-button"
              onClick={openAddForm}
            >
              + Add Contact
            </button>

          </div>

        ) : (

          filteredContacts.map((contact) => (

            <div
              className="contact-card"
              key={contact.id}
            >

              {/* CONTACT AVATAR */}

              <div className="contact-avatar">
                {contact.name
                  .charAt(0)
                  .toUpperCase()}
              </div>


              {/* CONTACT INFORMATION */}

              <div className="contact-info">

                <h3>
                  {contact.name}
                </h3>

                <p>
                  📧 {contact.email}
                </p>

                <p>
                  📞 {contact.phone}
                </p>

              </div>


              {/* ACTION BUTTONS */}

              <div className="card-actions">

                <button
                  className="edit-button"
                  onClick={() =>
                    startEdit(contact)
                  }
                >
                  ✏️ Edit
                </button>

                <button
                  className="delete-button"
                  onClick={() =>
                    deleteContact(contact.id)
                  }
                >
                  🗑️ Delete
                </button>

              </div>

            </div>

          ))

        )}

      </div>


      {/* =========================
          ADD / EDIT MODAL
      ========================= */}

      {showForm && (

        <div className="modal-overlay">

          <div className="modal">

            <button
              className="close-button"
              onClick={closeForm}
            >
              ×
            </button>


            <h2>
              {editingId !== null
                ? "Edit Contact"
                : "Add New Contact"}
            </h2>

            <p>
              {editingId !== null
                ? "Update your contact details."
                : "Enter the contact details below."}
            </p>


            {/* FORM */}

            <form
              onSubmit={
                editingId !== null
                  ? updateContact
                  : saveContact
              }
            >

              <label>
                Full Name
              </label>

             <input
  type="text"
  placeholder="Enter full name"
  value={name}
  className={errors.name ? "input-error" : ""}
  onChange={(e) => {
    setName(e.target.value);

    if (errors.name) {
      setErrors({
        ...errors,
        name: "",
      });
    }
  }}
/>

{errors.name && (
  <span className="error-message">
    {errors.name}
  </span>
)}


              <label>
                Email Address
              </label>

              <input
  type="email"
  placeholder="Enter email address"
  value={email}
  className={errors.email ? "input-error" : ""}
  onChange={(e) => {
    setEmail(e.target.value);

    if (errors.email) {
      setErrors({
        ...errors,
        email: "",
      });
    }
  }}
/>

{errors.email && (
  <span className="error-message">
    {errors.email}
  </span>
)}


              <label>
                Phone Number
              </label>

              <input
  type="text"
  placeholder="Enter phone number"
  value={phone}
  className={errors.phone ? "input-error" : ""}
  onChange={(e) => {
    setPhone(e.target.value);

    if (errors.phone) {
      setErrors({
        ...errors,
        phone: "",
      });
    }
  }}
/>

{errors.phone && (
  <span className="error-message">
    {errors.phone}
  </span>
)}


              {/* FORM BUTTONS */}

              <div className="form-actions">

                <button
                  type="button"
                  className="cancel-button"
                  onClick={closeForm}
                >
                  Cancel
                </button>

                <button
                  type="submit"
                  className="save-button"
                >
                  {editingId !== null
                    ? "Update Contact"
                    : "Save Contact"}
                </button>

              </div>

            </form>

          </div>

        </div>

      )}

    </div>
  );
}

export default App;