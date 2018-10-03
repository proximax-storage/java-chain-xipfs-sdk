/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.proximax.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class ParameterValidationUtilsTest {

    @Test
    public void shouldThrowExceptionWhenNotValidOnCheckParameter() {
        try {
            ParameterValidationUtils.checkParameter(false, "INVALID");
            fail("should have thrown exception");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("INVALID"));
        }
    }

    @Test
    public void shouldDoNothingOnCheckParameterWhenValid() {
        ParameterValidationUtils.checkParameter(true, "INVALID");
    }

    @Test
    public void shouldThrowExceptionWhenNotValidOnCheckParameterSupplier() {
        try {
            ParameterValidationUtils.checkParameter(() -> false, "INVALID");
            fail("should have thrown exception");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("INVALID"));
        }
    }

    @Test
    public void shouldThrowExceptionWhenErrorOnCheckParameterSupplier() {
        try {
            ParameterValidationUtils.checkParameter(
                    () -> {
                        if (false)
                            return true;
                        else
                            throw new IllegalArgumentException("not valid");
                    }, "INVALID");
            fail("should have thrown exception");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("INVALID"));
        }
    }

    @Test
    public void shouldDoNothingOnCheckParameterSupplierWhenValid() {
        ParameterValidationUtils.checkParameter(() -> true, "INVALID");
    }
}
