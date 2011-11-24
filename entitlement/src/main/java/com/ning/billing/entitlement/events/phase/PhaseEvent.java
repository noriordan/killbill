/*
 * Copyright 2010-2011 Ning, Inc.
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

package com.ning.billing.entitlement.events.phase;


import org.joda.time.DateTime;

import com.ning.billing.entitlement.alignment.IPlanAligner.TimedPhase;
import com.ning.billing.entitlement.api.user.Subscription;
import com.ning.billing.entitlement.events.EventBase;


public class PhaseEvent extends EventBase implements IPhaseEvent {

    private final String phaseName;

    public PhaseEvent(PhaseEventBuilder builder) {
        super(builder);
        this.phaseName = builder.getPhaseName();
    }

    @Override
    public EventType getType() {
        return EventType.PHASE;
    }

    @Override
    public String getPhase() {
        return phaseName;
    }

    @Override
    public String toString() {
        return "PhaseEvent [getId()= " + getId()
        		+ ", phaseName=" + phaseName
        		+ ", getType()=" + getType()
                + ", getPhase()=" + getPhase()
                + ", getRequestedDate()=" + getRequestedDate()
                + ", getEffectiveDate()=" + getEffectiveDate()
                + ", getActiveVersion()=" + getActiveVersion()
                + ", getProcessedDate()=" + getProcessedDate()
                + ", getSubscriptionId()=" + getSubscriptionId()
                + ", isActive()=" + isActive() + "]\n";
    }

    public static final IPhaseEvent getNextPhaseEvent(TimedPhase nextTimedPhase, Subscription subscription, DateTime now) {
        return (nextTimedPhase == null) ?
                null :
                    new PhaseEvent(new PhaseEventBuilder()
                        .setSubscriptionId(subscription.getId())
                        .setRequestedDate(now)
                        .setEffectiveDate(nextTimedPhase.getStartPhase())
                        .setProcessedDate(now)
                        .setActiveVersion(subscription.getActiveVersion())
                        .setPhaseName(nextTimedPhase.getPhase().getName()));
    }
}
