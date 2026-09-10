document .getElementById("registerForm") .addEventListener("submit", async function(event) {

// Stop the browser from refreshing the page event.preventDefault();
const username = document.getElementById("username").value;
const password = document.getElementById("password").value;
const role = document.getElementById("role").value;
const message = document.getElementById("message");
try {
const response = await fetch( "/api/v1/users/register", { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify({ username: username, password: password, role: role }) } );
const data = await response.json();
if (response.ok) {
message.innerHTML = ` <div class="alert alert-success"> Registration successful! </div> `;
document .getElementById("registerForm") .reset(); } else {
message.innerHTML = ` <div class="alert alert-danger"> ${data.message || "Registration failed"} </div> `; } } catch (error) { message.innerHTML = ` <div class="alert alert-danger"> Could not connect to the server. </div> `;
console.error(error); } });