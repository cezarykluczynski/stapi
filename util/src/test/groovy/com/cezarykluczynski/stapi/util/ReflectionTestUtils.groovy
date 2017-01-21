package com.cezarykluczynski.stapi.util

import java.beans.BeanInfo
import java.beans.Introspector
import java.beans.PropertyDescriptor

class ReflectionTestUtils  {

	static int getNumberOfTrueBooleanFields(Object object) {
		int numberOfTrueBooleanFields = 0
		BeanInfo beanInfo = Introspector.getBeanInfo(object.class)

		for (PropertyDescriptor propertyDesc : beanInfo.propertyDescriptors) {
			Object value = propertyDesc.readMethod.invoke(object)
			if (value == true) {
				numberOfTrueBooleanFields++
			}
		}

		numberOfTrueBooleanFields
	}

	static int getNumberOfNotNullFields(Object object) {
		int numberOfNotNullFields = 0
		BeanInfo beanInfo = Introspector.getBeanInfo(object.class)

		for (PropertyDescriptor propertyDesc : beanInfo.propertyDescriptors) {
			Object value = propertyDesc.readMethod.invoke(object)
			if (value != null && propertyDesc.name != 'class') {
				numberOfNotNullFields++
			}
		}

		numberOfNotNullFields
	}

}
