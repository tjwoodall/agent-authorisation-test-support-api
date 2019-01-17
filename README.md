# Agent Authorisation Test Support API documentation version 1.0

### Overview
The Agent Authorisation Test Support API prepares Client's data in the stub and returns a knownFact required when creating agent authorisation request using POST /agents/:arn/invitations.

---

## /agent-authorisation-test-support

### /agent-authorisation-test-support/known-facts/mtd-vat/vrn/{vrn}

* **vrn**: VAT Registration Number
    * Type: string
    
    * Required: true

#### **GET**:

###### Headers

| Name | Type | Description | Required | Examples |
|:-----|:----:|:------------|:--------:|---------:|
| Accept | string | Specifies the response format and the [version](/api-documentation/docs/reference-guide#versioning) of the API to be used. | true | ``` application/vnd.hmrc.1.0+json ```  |

### Response code: 200

#### application/json (application/json) 
Returns VAT Registration Date matching client's VRN for the purpose of MTD-VAT agent authorisation request

```
{
  "service": ["MTD-VAT"],
  "clientIdType": "vrn",
  "clientId": "101747696",
  "knownFact": "2007-05-18"
}
```

### /agent-authorisation-test-support/known-facts/mtd-it/nino/{nino}

* **nino**: National Insurance Number
    * Type: string
    
    * Required: true

#### **GET**:

###### Headers

| Name | Type | Description | Required | Examples |
|:-----|:----:|:------------|:--------:|---------:|
| Accept | string | Specifies the response format and the [version](/api-documentation/docs/reference-guide#versioning) of the API to be used. | true | ``` application/vnd.hmrc.1.0+json ```  |

### Response code: 200

#### application/json (application/json) 
Returns business postcode matching client's NINO for the purpose of MTD-IT agent authorisation request

```
{
  "service": ["MTD-IT"],
  "clientIdType": "ni",
  "clientId": "AA999999A",
  "knownFact": "AA11 1AA"
}
```

