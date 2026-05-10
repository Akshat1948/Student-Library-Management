import { useState, useEffect } from 'react'
import './App.css'

interface Book {
  isbn: string;
  title: string;
  author: string;
  status: string;
  price: number;
}

function App() {
  const [books, setBooks] = useState<Book[]>([]);
  const [newBook, setNewBook] = useState({ isbn: '', title: '', author: '', price: '' });
  const [studentId, setStudentId] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const res = await fetch(`${API_URL}/books`);
      const data = await res.json();
      setBooks(data);
    } catch (err) {
      console.error('Failed to fetch books', err);
    }
  };

  const showMessage = (msg: string, isError = false) => {
    if (isError) {
      setError(msg);
      setMessage('');
    } else {
      setMessage(msg);
      setError('');
    }
    setTimeout(() => {
      setMessage('');
      setError('');
    }, 5000);
  };

  const addBook = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API_URL}/books`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...newBook, price: parseFloat(newBook.price) }),
      });
      if (res.ok) {
        setNewBook({ isbn: '', title: '', author: '', price: '' });
        fetchBooks();
        showMessage('Book added successfully!');
      } else {
        showMessage('Failed to add book', true);
      }
    } catch (err) {
      showMessage('Connection error', true);
    }
  };

  const borrowBook = async (isbn: string) => {
    if (!studentId) {
      showMessage('⚠️ ACTION REQUIRED: Enter a Student ID in the box above first!', true);
      // Focus the input if it's missing
      document.getElementById('student-id-input')?.focus();
      return;
    }
    try {
      const res = await fetch(`${API_URL}/borrow`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ isbn, studentId: parseInt(studentId) }),
      });
      if (res.ok) {
        showMessage('✅ Book borrowed successfully!');
        fetchBooks();
      } else {
        showMessage('❌ This book is already issued or not found.', true);
      }
    } catch (err) {
      showMessage('Connection error', true);
    }
  };

  const returnBook = async (isbn: string) => {
    try {
      const res = await fetch(`${API_URL}/return/${isbn}`, { method: 'POST' });
      const data = await res.json().catch(() => ({}));
      
      if (res.ok) {
        showMessage('✅ Book returned to inventory!');
        fetchBooks();
      } else {
        showMessage(`❌ Error: ${data.error || 'Server error'}`, true);
      }
    } catch (err) {
      showMessage('Connection error', true);
    }
  };

  const sortBooks = async () => {
    try {
      await fetch(`${API_URL}/sort`, { method: 'POST' });
      fetchBooks();
      showMessage('Inventory sorted by ISBN!');
    } catch (err) {
      showMessage('Failed to sort', true);
    }
  };

  return (
    <div className="container">
      <header>
        <h1>📚 Student Library</h1>
        <p>A modern way to manage your collection</p>
      </header>

      {message && <div className="message-banner msg-info">{message}</div>}
      {error && <div className="message-banner msg-error">{error}</div>}

      <div className="grid">
        <section className="card">
          <h2>Add New Book</h2>
          <form onSubmit={addBook}>
            <div className="form-group">
              <label>ISBN NUMBER</label>
              <input value={newBook.isbn} onChange={e => setNewBook({...newBook, isbn: e.target.value})} placeholder="e.g. 101" required />
            </div>
            <div className="form-group">
              <label>BOOK TITLE</label>
              <input value={newBook.title} onChange={e => setNewBook({...newBook, title: e.target.value})} placeholder="e.g. Java Programming" required />
            </div>
            <div className="form-group">
              <label>AUTHOR NAME</label>
              <input value={newBook.author} onChange={e => setNewBook({...newBook, author: e.target.value})} placeholder="e.g. James Gosling" required />
            </div>
            <div className="form-group">
              <label>PRICE ($)</label>
              <input type="number" step="0.01" value={newBook.price} onChange={e => setNewBook({...newBook, price: e.target.value})} placeholder="0.00" required />
            </div>
            <button type="submit" className="btn-primary">ADD TO COLLECTION</button>
          </form>
        </section>

        <section className="card">
          <h2>Library Controls</h2>
          <div className="borrow-section">
            <label>ENTER STUDENT ID TO BORROW</label>
            <input id="student-id-input" type="number" value={studentId} onChange={e => setStudentId(e.target.value)} placeholder="Enter ID (e.g. 501)" style={{marginBottom: '1rem'}} />
            <p style={{fontSize: '0.8rem', color: 'var(--text-light)', marginBottom: '1rem'}}>* You must enter an ID here before clicking "Borrow" on a book below.</p>
          </div>
          
          <div style={{marginTop: '2rem'}}>
            <button onClick={sortBooks} style={{background: '#475569', color: 'white', width: '100%'}}>SORT INVENTORY (ISBN)</button>
          </div>
        </section>
      </div>

      <section className="card">
        <h2>Library Inventory ({books.length} Books)</h2>
        <div className="book-list">
          {books.length === 0 ? (
            <p style={{textAlign: 'center', padding: '2rem', color: 'var(--text-light)'}}>The library is currently empty. Add your first book above!</p>
          ) : (
            books.map(book => (
              <div key={book.isbn} className="book-item">
                <div className="book-info">
                  <h3>{book.title}</h3>
                  <p><strong>Author:</strong> {book.author}</p>
                  <p><strong>ISBN:</strong> {book.isbn}</p>
                <p style={{fontWeight: 'bold', color: 'var(--primary)', marginTop: '0.5rem'}}>₹{book.price.toFixed(2)}</p>
                </div>
                <div className="status-row">
                  <span className={`badge ${book.status === 'available' ? 'badge-available' : 'badge-issued'}`}>
                    {book.status === 'available' ? '✅ Available' : '🔴 Issued / Borrowed'}
                  </span>
                  <div className="actions">
                    {book.status === 'available' ? (
                      <button onClick={() => borrowBook(book.isbn)} className="btn-primary">BORROW BOOK</button>
                    ) : (
                      <button onClick={() => returnBook(book.isbn)} style={{background: 'var(--success)', color: 'white'}}>RETURN BOOK</button>
                    )}
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </section>
    </div>
  )
}

export default App
