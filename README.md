# Spring Authorization Server work with KeyCloak server
## Work flow of KeyCloak fallow the bellow steps

## Working with OAuth
> Download and run at docker fallow the commands
```
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.6 start-dev
```
Go to the browser and put the details port:host `localhost:8080`
Here in login page put the default username and password `admin`
After entering to the application
+ Create a new realm
+ Create a new User (chitra)
  - Provide the required impormations of user and select the autherities
  - *Generated Id* is important like (030b234c-9d1c-4046-8ded-2f955ee4a8ed)
  - Go to Credentials and set the password and remember the password (chitra)
  - For check the user credentials go to new tab and fill the details `http://localhost:8080/realms/{realm_name}/account`
    ```
	http://localhost:8080/realms/appdeveloper/account
	```
	Here you need to Provde the username/email and password for validation
+ Create a clients (`With whom you want share you resources`)
  - select the `Client Type` as `OpenID Connect` and provide the `Client ID<sub>*</sub>` like `photo-access`
  - Provide the other required details like `Valid redirect URIs` and example `https://www.keycloak.org/app/*` for testing purpose
  - And Copy the `Client Secret` like `C181JCUrCd1VcQu18EgFq3ww3K3H7cZu` and use for future.
+ For testing purpose
  - Goto [https://www.keycloak.org/app](https://www.keycloak.org/app)
  - Provide the Realm and Client and press Save and Login with User details
  - In Browser go to the Developer mode(press `F12`) then `network` then find the `token` open the response like below.
    ```json
	{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJVRGdjbzhpazZzT1hhY1FsdUYxTjc3WDE1ZzZxN08zWlRTOHk0M1R6RFpjIn0.eyJleHAiOjE3MjcxNTg0NTAsImlhdCI6MTcyNzE1ODE1MCwiYXV0aF90aW1lIjoxNzI3MTU4MTQ5LCJqdGkiOiIzNDYyNmU4NS0zZDU0LTRkYmQtOWZkMS1hNDlkY2Q0OTQwZGEiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvcmVhbG1zL2RldmVsb3BlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJhMTQzNTM2Yy05YTU2LTQ4ZjQtYWVkZC02ZWNiY2MxZTVmZTAiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJwb2ludGVycyIsInNpZCI6IjQwN2VhZDkyLWFmZmUtNDVlNi1iY2I4LTE5MzdiZjkwZTQ4MCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly93d3cua2V5Y2xvYWsub3JnIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1kZXZlbG9wZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiUGFkbWFuYWJoYSBTYWh1IiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlciIsImdpdmVuX25hbWUiOiJQYWRtYW5hYmhhIiwiZmFtaWx5X25hbWUiOiJTYWh1IiwiZW1haWwiOiJzcGFkbTIyQGdtYWlsLmNvbSJ9.oSSZvtY_8W-JjZLPfn2V-ljYdXNCbKbDszHbu-Qjym7kGTvwKJXHX_w165dHhsV98ymymKWIgfzCAfadsiqnU_uh8u1WvhyFAUMgznT9JG8xNjfWxHmPp7GIS3Jra56eBSsjucoIUW7odLcBySAC9J_5MKkbEIBGs2ckHIk6fgoLWgyHxIYHzaNV8UCq6DU5zyFAt0KiGA-RAKboMnBrbHrPbDx1Hvtuy25Bni1ZT6aAgowVk8qeNWMv0xS6Da2EpGJPVOtFI-GjsEZZO4ZYVS5LSwu-D3GJ4T-scqOJWNHyH1ZJt3jVnRo73qEBw6C-LASNt83X5TgrhjM5GCPDRg",
    "expires_in": 300,
    "refresh_expires_in": 1800,
    "refresh_token": "eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxYzFjODA1Zi1kZTgzLTQ4ZWItYTU4Zi01ZGUyMTYwYzQ4NGUifQ.eyJleHAiOjE3MjcxNTk5NTAsImlhdCI6MTcyNzE1ODE1MCwianRpIjoiNThlMTI0M2ItYTJjNC00NjQ5LTk1NzQtZjhkY2E2Y2RkOTM4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9kZXZlbG9wZXIiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvcmVhbG1zL2RldmVsb3BlciIsInN1YiI6ImExNDM1MzZjLTlhNTYtNDhmNC1hZWRkLTZlY2JjYzFlNWZlMCIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJwb2ludGVycyIsInNpZCI6IjQwN2VhZDkyLWFmZmUtNDVlNi1iY2I4LTE5MzdiZjkwZTQ4MCIsInNjb3BlIjoib3BlbmlkIHJvbGVzIHdlYi1vcmlnaW5zIGVtYWlsIHByb2ZpbGUgYWNyIGJhc2ljIn0.qEEgo9eJmvnFDK6MxFlKauH98acMeYoh22vir8Oxe2dIVPIBEwZVpCQtFxu9FCVeUPafx4WFrPa4jaJjAJnx_g",
    "token_type": "Bearer",
    "id_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJVRGdjbzhpazZzT1hhY1FsdUYxTjc3WDE1ZzZxN08zWlRTOHk0M1R6RFpjIn0.eyJleHAiOjE3MjcxNTg0NTAsImlhdCI6MTcyNzE1ODE1MCwiYXV0aF90aW1lIjoxNzI3MTU4MTQ5LCJqdGkiOiI1YTk1YmFhZi01ODQ5LTQ1NzUtYWFhOS0yNjliMTIzOTFlNWUiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvcmVhbG1zL2RldmVsb3BlciIsImF1ZCI6InBvaW50ZXJzIiwic3ViIjoiYTE0MzUzNmMtOWE1Ni00OGY0LWFlZGQtNmVjYmNjMWU1ZmUwIiwidHlwIjoiSUQiLCJhenAiOiJwb2ludGVycyIsIm5vbmNlIjoiMTRmNDYyYmQtYmQwNi00ZGM0LWI3ODYtZDE5YTdmNjJiMWIxIiwic2lkIjoiNDA3ZWFkOTItYWZmZS00NWU2LWJjYjgtMTkzN2JmOTBlNDgwIiwiYXRfaGFzaCI6Imo1NVhJUjl2ckNXU1JxeFFBZFNYRVEiLCJhY3IiOiIxIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiUGFkbWFuYWJoYSBTYWh1IiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlciIsImdpdmVuX25hbWUiOiJQYWRtYW5hYmhhIiwiZmFtaWx5X25hbWUiOiJTYWh1IiwiZW1haWwiOiJzcGFkbTIyQGdtYWlsLmNvbSJ9.jpI7c0IAieTH_VAtr4XLz8PFEZuUlSS8uK9SMmceJirt2G2yfPg4yA__w-u8Uva0KjnMp_1Xn2s8pDhOI_t-NBoiPq2O1YjWc4zFk0BviPW3F36G2VMrZIWXzXkhm4K7oWdSVZdybk9Woz9quy1cjzWjKP7e3iNeDGgeGeGRRwwli_gqheol1_OfS-m5VEGtEn9mU08hSbnxAvHDQHBHbHZ-2PRXH8gXnvxlZwCv5OmThfjXCYLAKyqkwppwzJYTv7RTTAGlaGS-FXZYLCnPYIPcl_UbrREgE7yLdtybmFBosRq5SqQpucSMNq_FD80Q9_1ZGFaUxzPKQ9TSfMeKEA",
    "not-before-policy": 0,
    "session_state": "407ead92-affe-45e6-bcb8-1937bf90e480",
    "scope": "openid email profile"
	}```
  
