/*******************************************************************************
 * Copyright (c) 2016 Daniel Murygin <dm{a}sernet{dot}de>.
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package org.verinice.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@SpringBootApplication
@Configuration
@ComponentScan({"org.verinice.persistence",
                "org.verinice.rest",
                "org.verinice.service"})
public class Application {

    public Application() {
        super();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
          .info(new Info()
            .title("verinice.REST API")
            .version("0.5-SNAPSHOT")
            .termsOfService("https://shop.verinice.com/de/agb")
            .license(new License().name("LGPLv3").url("https://www.gnu.org/licenses/lgpl-3.0.de.html")));
	}
}
