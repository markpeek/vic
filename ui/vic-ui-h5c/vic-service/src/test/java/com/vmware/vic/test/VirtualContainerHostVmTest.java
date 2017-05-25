/*

Copyright 2017 VMware, Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/
package com.vmware.vic.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.net.URI;

import com.vmware.vic.ModelObjectUriResolver;
import com.vmware.vic.model.ModelObject;
import com.vmware.vic.model.VirtualContainerHostVm;
import com.vmware.vic.model.constants.BaseVm;
import com.vmware.vic.model.constants.Vch;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.VirtualMachinePowerState;

public class VirtualContainerHostVmTest extends Common {
    private static final String VM_TYPE_VCHVM = "vic:VirtualContainerHostVm";
	private VirtualContainerHostVm _vm;

	@Before
	public void setModelObject() {
		_vm = getMockedVirtualContainerHostVm(
				"server1",
				"id1",
				"test vm",
				ManagedEntityStatus.GREEN,
				VirtualMachinePowerState.POWERED_ON,
				"ChFtuw==");
	}

	@Test
	public void testGetType() {
		assertEquals(VM_TYPE_VCHVM, _vm.getType());
	}

	@Test
	public void testGetId() {
		assertEquals("server1/id1", _vm.getId());
	}

	@Test
	public void testGetUri() {
		ModelObjectUriResolver uriResolver = new ModelObjectUriResolver();
		URI uri = _vm.getUri(uriResolver);
		assertEquals(String.format(
                "urn:vic:%s:%s", VM_TYPE_VCHVM, "server1/id1"),
                uri.toString());
	}

	@Test
	public void testGetProperty() {
		assertFalse(_vm.getProperty(BaseVm.VM_NAME)
		        .equals(ModelObject.UNSUPPORTED_PROPERTY));
		assertFalse(_vm.getProperty(BaseVm.VM_OVERALL_STATUS)
		        .equals(ModelObject.UNSUPPORTED_PROPERTY));
		assertFalse(_vm.getProperty(BaseVm.Runtime.VM_POWERSTATE_FULLPATH)
		        .equals(ModelObject.UNSUPPORTED_PROPERTY));
		assertFalse(_vm.getProperty(Vch.VM_CLIENT_IP)
		        .equals(ModelObject.UNSUPPORTED_PROPERTY));
		assertFalse(_vm.getProperty(BaseVm.VM_OVERALLCPUUSAGE)
		        .equals(ModelObject.UNSUPPORTED_PROPERTY));
		assertFalse(_vm.getProperty(BaseVm.VM_GUESTMEMORYUSAGE)
		        .equals(ModelObject.UNSUPPORTED_PROPERTY));
		assertFalse(_vm.getProperty(BaseVm.VM_COMMITTEDSTORAGE)
		        .equals(ModelObject.UNSUPPORTED_PROPERTY));
		assertTrue(_vm.getProperty("iDontExist").equals(ModelObject.UNSUPPORTED_PROPERTY));
	}

	@Test
	public void testGetters() {
		assertEquals(_vm.getName(), "test vm");
		assertEquals(_vm.getOverallStatus(), "GREEN");
		assertEquals(_vm.getPowerState(), "POWERED_ON");
		assertEquals(_vm.getClientIp(), "10.17.109.187");
		assertEquals(_vm.getOverallCpuUsage(), 1000);
		assertEquals(_vm.getGuestMemoryUsage(), 500);
		assertEquals(_vm.getCommittedStorage(), (long)123456789);
	}
}
