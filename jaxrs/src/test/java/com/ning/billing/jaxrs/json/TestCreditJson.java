/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.jaxrs.json;

import java.math.BigDecimal;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class TestCreditJson {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test(groups = "fast")
    public void testJson() throws Exception {
        final BigDecimal creditAmount = BigDecimal.TEN;
        final UUID invoiceId = UUID.randomUUID();
        final String invoiceNumber = UUID.randomUUID().toString();
        final DateTime requestedDate = new DateTime(DateTimeZone.UTC);
        final DateTime effectiveDate = new DateTime(DateTimeZone.UTC);
        final String reason = UUID.randomUUID().toString();
        final CreditJson creditJson = new CreditJson(creditAmount, invoiceId, invoiceNumber, requestedDate, effectiveDate, reason);
        Assert.assertEquals(creditJson.getRequestedDate(), requestedDate);
        Assert.assertEquals(creditJson.getEffectiveDate(), effectiveDate);
        Assert.assertEquals(creditJson.getCreditAmount(), creditAmount);
        Assert.assertEquals(creditJson.getInvoiceId(), invoiceId);
        Assert.assertEquals(creditJson.getInvoiceNumber(), invoiceNumber);
        Assert.assertEquals(creditJson.getReason(), reason);

        final String asJson = mapper.writeValueAsString(creditJson);
        Assert.assertEquals(asJson, "{\"creditAmount\":" + creditJson.getCreditAmount() + "," +
                "\"invoiceId\":\"" + creditJson.getInvoiceId().toString() + "\"," +
                "\"invoiceNumber\":\"" + creditJson.getInvoiceNumber() + "\"," +
                "\"requestedDate\":\"" + creditJson.getRequestedDate() + "\"," +
                "\"effectiveDate\":\"" + creditJson.getEffectiveDate() + "\"," +
                "\"reason\":\"" + creditJson.getReason() + "\"}");

        final CreditJson fromJson = mapper.readValue(asJson, CreditJson.class);
        Assert.assertEquals(fromJson, creditJson);
    }
}
