/*
 * Copyright (c) 2021 Robert Bosch Manufacturing Solutions GmbH
 *
 * See the AUTHORS file(s) distributed with this work for additional
 * information regarding authorship.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package io.openmanufacturing.sds.aspectmetamodel;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.openmanufacturing.sds.validation.SemanticError;

public class RangeShapeTest extends AbstractShapeTest {

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testRangeValidationExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "range-shape", "TestRange", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testMissingRequiredPropertiesExpectFailure2( final KnownVersion metaModelVersion ) {
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestRangeMissingRequiredProperties";

      final SemanticError resultForMinAndMax = new SemanticError( MESSAGE_RANGE_NEEDS_MIN_MAX,
            focusNode, "", VIOLATION_URN, focusNode );
      expectSemanticValidationErrors( "range-shape", "TestRangeMissingRequiredProperties",
            metaModelVersion, resultForMinAndMax );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInvalidDataTypeForMinAndMaxValue( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestRangeWithInvalidMinAndMaxValueDataType";

      final SemanticError resultForMinValue = new SemanticError(
            validator.getMessageText( "bamm-c:RangeShape", "bamm-c:minValue", "ERR_WRONG_DATATYPE", metaModelVersion ),
            focusNode, bammUrns.minValueUrn, VIOLATION_URN, "" );
      final SemanticError resultForMaxValue = new SemanticError(
            validator.getMessageText( "bamm-c:RangeShape", "bamm-c:maxValue", "ERR_WRONG_DATATYPE", metaModelVersion ),
            focusNode, bammUrns.maxValueUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "range-shape", "TestRangeWithInvalidMinAndMaxValueDataType",
            metaModelVersion, resultForMinValue, resultForMaxValue );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInvalidValueForLowerBoundDefinition( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String minimum = String
            .format( bammUrns.minValueUrn.replace( "minValue", "MINIMUM" ), metaModelVersion.toVersionString() );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestRangeWithInvalidLowerBoundDefinition";

      final SemanticError invalidLowerBoundDefinitionValue = new SemanticError(
            MESSAGE_INVALID_LOWER_BOUND_DEFINITION_VALUE, focusNode, bammUrns.lowerBoundDefinition, VIOLATION_URN,
            minimum );
      expectSemanticValidationErrors( "range-shape", "TestRangeWithInvalidLowerBoundDefinition",
            metaModelVersion, invalidLowerBoundDefinitionValue );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInvalidValueForUpperBoundDefinition( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String maximum = String
            .format( bammUrns.maxValueUrn.replace( "maxValue", "MAXIMUM" ), metaModelVersion.toVersionString() );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestRangeWithInvalidUpperBoundDefinition";

      final SemanticError invalidUpperBoundDefinitionValue = new SemanticError(
            MESSAGE_INVALID_UPPER_BOUND_DEFINITION_VALUE, focusNode, bammUrns.upperBoundDefinition, VIOLATION_URN,
            maximum );
      expectSemanticValidationErrors( "range-shape", "TestRangeWithInvalidUpperBoundDefinition",
            metaModelVersion, invalidUpperBoundDefinitionValue );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testMultipleBoundDefinitions( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestRangeWithMultipleBoundDefinitions";

      final SemanticError resultForUpperBoundDefinition = new SemanticError(
            MESSAGE_INVALID_UPPER_BOUND_DEFINITION_VALUE, focusNode, bammUrns.upperBoundDefinition, VIOLATION_URN,
            "" );
      final SemanticError resultForLowerBoundDefinition = new SemanticError(
            MESSAGE_INVALID_LOWER_BOUND_DEFINITION_VALUE, focusNode, bammUrns.lowerBoundDefinition, VIOLATION_URN,
            "" );
      expectSemanticValidationErrors( "range-shape", "TestRangeWithMultipleBoundDefinitions",
            metaModelVersion, resultForUpperBoundDefinition, resultForLowerBoundDefinition );
   }
}
