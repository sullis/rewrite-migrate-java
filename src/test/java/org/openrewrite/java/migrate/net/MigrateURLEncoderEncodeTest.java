/*
 * Copyright 2024 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.java.migrate.net;

import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;
import static org.openrewrite.java.Assertions.javaVersion;

class MigrateURLEncoderEncodeTest implements RewriteTest {
    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new MigrateURLEncoderEncode())
          .allSources(s -> s.markers(javaVersion(10)));
    }

    @DocumentExample
    @Test
    void urlEncoderEncode() {
        //language=java
        rewriteRun(
          java(
            """
              package org.openrewrite.example;

              import java.net.URLEncoder;

              class Test {
                  public static void method(String url) {
                      String message = URLEncoder.encode(url);
                  }
              }
              """,
            """
              package org.openrewrite.example;

              import java.net.URLEncoder;
              import java.nio.charset.StandardCharsets;

              class Test {
                  public static void method(String url) {
                      String message = URLEncoder.encode(url, StandardCharsets.UTF_8);
                  }
              }
              """
          )
        );
    }
}
