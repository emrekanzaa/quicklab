QuickLab is a desktop library management system where two types of users — Members and Librarians — interact with a shared book catalog. Members browse books, place rental requests, return books, and leave reviews. 
Librarians approve or reject requests, manage users, and moderate content. 
The project was built as an academic exercise to show real, applied use of object-oriented design principles and Gang-of-Four patterns inside a working GUI application — not just in a textbook example.

WHAT THE SYSTEM CAN DO
*Book catalog
Visual card grid with custom cover art, ratings, and genre tags. Click any card for full details.
*Rental lifecycle
Full flow: request → approve/reject → return. Due-date tracking with overdue detection.
*Reviews & ratings
Members rate books on return. Librarians can toggle spoiler warnings on any review.
*Notifications
Approval and rejection trigger Swing dialog notifications via the Notification pattern.
*Role-based UI
Login resolves to Member or Librarian view. The "Users" tab only appears for Librarians.
*Priority delivery
Members choose standard or priority. Price composed via Decorator at runtime.


TWO ROLES, ONE CODEBASE
Member                                            Librarian
login: emre / emre123                             login: admin / admin123
Browse the book catalog                           All member capabilities
Request standard or priority rental               Approve or reject pending rentals
View own active rentals                           Mark books as returned / collected
Return a book with optional review                Add new books to the catalog
Rate books 1–5 stars, flag spoilers               Add new users (Member or Librarian)
View own review history                           Block / unblock member accounts
                                                  Toggle spoiler flag on any review




OOP principles at a glance 
Principle	                              Where	                                  Example
Encapsulation	                          All model classes	                      Book.isAvailable → private
                                                                                setAvailable(boolean) → public
Inheritance	                            User → Member, Librarian	              login(), getUserID() inherited
                                                                                getRole() overridden per subclass
Polymorphism	                          getRole(), calculatePrice()	            Member.getRole() → "Member"
                                                                                Librarian.getRole() → "Librarian"
Abstraction	                            User, RentalService, Notification	      abstract class User, interface RentalService,
                                                                                interface Notification
