package org.example.webserver.server.rest;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import lombok.experimental.FieldDefaults;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicUser {
    
    @XmlAttribute
    int id;
    
    String firstName;
    
    String lastName;
}
