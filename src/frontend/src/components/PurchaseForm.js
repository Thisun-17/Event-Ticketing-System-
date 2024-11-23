// src/components/PurchaseForm.js
import React, { useState } from 'react';
import {
  Paper,
  TextField,
  Button,
  Typography,
  Box
} from '@mui/material';

function PurchaseForm() {
  const [ticketId, setTicketId] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Attempting to purchase ticket: ${ticketId}`);
    setTicketId('');
  };

  return (
    <Paper sx={{ p: 2, mt: 2 }}>
      <Typography variant="h6" gutterBottom>
        Purchase Ticket
      </Typography>
      <Box component="form" onSubmit={handleSubmit}>
        <TextField
          fullWidth
          label="Ticket ID"
          value={ticketId}
          onChange={(e) => setTicketId(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
        >
          Purchase
        </Button>
      </Box>
    </Paper>
  );
}

export default PurchaseForm;