package com.kravel.server.auth.security.util;

import com.kravel.server.auth.security.util.jwt.HeaderTokenExtractor;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;


class HeaderTokenExtractorTest {

    private HeaderTokenExtractor extractor = new HeaderTokenExtractor();
    private String header;

    @Before
    public void setUp() {
        this.header = "Bearer dsfdasfsdafsadf";
    }

    @Test
    public void TEST_JWT_EXTRACT() {
        then(extractor.extract(this.header)).isEqualTo("dsfdasfsdafsadf");
    }

}