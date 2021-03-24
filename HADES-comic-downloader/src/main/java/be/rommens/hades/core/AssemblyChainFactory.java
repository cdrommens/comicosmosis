package be.rommens.hades.core;

import be.rommens.hades.assembler.IssueAssemblyChain;

/**
 * User : cederik
 * Date : 23/04/2020
 * Time : 15:42
 */
public interface AssemblyChainFactory<I> {

    IssueAssemblyChain createAssemblyChain(I input);
}
