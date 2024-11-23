// src/App.js
import React from 'react';
import { Container } from '@mui/material';
import Header from './components/Header';
import TicketList from './components/TicketList';
import PurchaseForm from './components/PurchaseForm';

function App() {
  return (
    <div>
      <Header />
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <TicketList />
        <PurchaseForm />
      </Container>
    </div>
  );
}

export default App;