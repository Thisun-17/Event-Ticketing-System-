# Event Ticketing System

A Java implementation of a concurrent ticket booking system using Producer-Consumer pattern.

## Week 01 Progress

### Implemented Classes:
1. **Core Components:**
   ```
   src/main/java/org/example/ticketing/
   ├── config/
   │   └── Configuration.java
   ├── model/
   │   ├── Ticket.java
   │   └── TicketPool.java
   ├── producer/
   │   └── Vendor.java
   └── consumer/
       └── Customer.java
   ```

### Features Implemented:
- ✓ Basic ticket creation and management
- ✓ Thread-safe ticket pool operations
- ✓ Producer (Vendor) implementation
- ✓ Consumer (Customer) implementation
- ✓ System configuration management

### How to Run:
1. Clone the repository
2. Open project in IntelliJ IDEA
3. Run Main.java

### Project Structure:
- `Configuration`: Manages system settings
- `Ticket`: Represents a single ticket
- `TicketPool`: Handles thread-safe ticket operations
- `Vendor`: Creates and adds tickets (Producer)
- `Customer`: Purchases tickets (Consumer)

### Next Steps (Week 02):
- [ ] Enhance thread synchronization
- [ ] Add comprehensive testing
- [ ] Implement monitoring system
- [ ] Support multiple vendors and customers