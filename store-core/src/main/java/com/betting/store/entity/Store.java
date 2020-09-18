package com.betting.store.entity;

import com.betting.store.util.HashcodeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "store")
public class Store {

	@Id
	private String id;
	private String name;
	@DBRef(lazy = true)
	private Set<Product> products;

	@Override
	public int hashCode() {
		final int PRIME = 79;
		return HashcodeUtil.buildHashcode(PRIME, getId());
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (o == this)
			return true;

		if (o.getClass() != getClass())
			return false;

		Store s = (Store) o;

		return new EqualsBuilder().
				append(getId(), s.getId()).
				isEquals();
	}

}
