package uk.gov.hmcts.probate.core.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.probate.client.backoffice.BackOfficeApi;
import uk.gov.hmcts.probate.model.backoffice.BackOfficeCallbackRequest;
import uk.gov.hmcts.reform.probate.model.cases.CaseInfo;
import uk.gov.hmcts.reform.probate.model.cases.ProbateCaseDetails;
import uk.gov.hmcts.reform.probate.model.cases.caveat.CaveatData;
import uk.gov.hmcts.reform.probate.model.cases.grantofrepresentation.GrantOfRepresentationData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BackOfficeServiceImplTest {

    private static final String CASE_ID = "42343543";
    private static final String SERVICE_AUTHORIZATION = "SERVICEAUTH1234567";
    private static final String AUTHORIZATION = "AUTH1234567";

    @Mock
    private BackOfficeApi backOfficeApi;

    @Mock
    private SecurityUtils securityUtils;

    @InjectMocks
    private BackOfficeServiceImpl backOfficeService;

    @Before
    public void setUp() {
        Mockito.when(securityUtils.getAuthorisation()).thenReturn(AUTHORIZATION);
        Mockito.when(securityUtils.getServiceAuthorisation()).thenReturn(SERVICE_AUTHORIZATION);
    }

    @Test
    public void shouldSendNotificationWhenCaseTypeIsCaveat() {
        ProbateCaseDetails probateCaseDetails = ProbateCaseDetails.builder()
            .caseInfo(CaseInfo.builder()
                .caseId(CASE_ID)
                .build())
            .caseData(CaveatData.builder().build())
            .build();

        backOfficeService.sendNotification(probateCaseDetails);

        verify(backOfficeApi).raiseCaveat(eq(AUTHORIZATION), eq(SERVICE_AUTHORIZATION), any(BackOfficeCallbackRequest.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCaseTypeIsGrantOfRepresentation() {
        ProbateCaseDetails probateCaseDetails = ProbateCaseDetails.builder()
            .caseInfo(CaseInfo.builder()
                .caseId(CASE_ID)
                .build())
            .caseData(GrantOfRepresentationData.builder().build())
            .build();

        backOfficeService.sendNotification(probateCaseDetails);
    }
}
